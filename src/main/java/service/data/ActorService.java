package service.data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;

import entity.Actor;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class ActorService {
	
	/**
     * Saves actors to the database.
     *
     * @param lines the lines of the actors CSV file
     * @param em the entity manager to persist data and create transaction
     */
    public static void saveActors(List<String> lines,EntityManager em ) {
    	EntityTransaction transaction= em.getTransaction();
        transaction.begin();
        for (int i = 1; i < lines.size(); i++) {
            String[] elements = lines.get(i).split(";", -1);
            if (elements.length == 7) {
                Actor actor = new Actor();
                LocationService.setPersonLocation(elements, actor, em);
                setActorBirthDate(elements, actor);
                setActorHeight(elements, actor);
                actor.setIdentity(elements[1]);
                actor.setIdImdb(elements[0]);
                actor.setUrl(elements[5]);
                em.persist(actor);
            }
        }
        transaction.commit();
    }
    
    /**
     * Sets the birth date of an actor.
     *
     * @param elements the elements of the actor CSV line
     * @param actor the actor entity
     */
    private static void setActorBirthDate(String[] elements, Actor actor) {
    	if(!elements[2].isEmpty()) {
			 try {
			        LocalDate result = LocalDate.parse(elements[2].trim(), DateTimeFormatter.ofPattern("MMMM d yyyy", Locale.ENGLISH));
			        actor.setBirthDate(result);
			    } catch (DateTimeParseException e) {
			        // Gestion de l'erreur
			        System.err.println("Erreur de parsing de la date pour l'élément : " + elements[2]);
			    }
		}
    }
    
    /**
     * Sets the height of an actor.
     *
     * @param elements the elements of the actor CSV line
     * @param actor the actor entity
     */
    private static void setActorHeight(String[] elements, Actor actor) {
        if (!elements[4].isEmpty()) {
            try {
                actor.setHeight(Double.parseDouble(elements[4].replace("m", "").replace(",", ".").replace("\u202F", " ")));
            } catch (NumberFormatException e) {
         // Gestion de l'erreur de conversion en double
                System.err.println("Erreur de conversion en double pour la hauteur : " + elements[4]);
            }
        }
    }

}
