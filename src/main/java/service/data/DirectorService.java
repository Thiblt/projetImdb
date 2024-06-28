package service.data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;

import entity.Director;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class DirectorService {
	/**
     * Saves directors to the database.
     *
     * @param lines the lines of the directors CSV file
     */
    public static void saveDirectors(List<String> lines ,EntityManager em ) {
    	EntityTransaction transaction= em.getTransaction();
        transaction.begin();
        for (int i = 1; i < lines.size(); i++) {
            String[] elements = lines.get(i).split(";", -1);
            Director director = new Director();
            LocationService.setPersonLocation(elements, director, em);
            setDirectorBirthDate(elements, director);
            director.setIdentity(elements[1]);
            director.setIdImdb(elements[0]);
            director.setUrl(elements[4]);
            em.persist(director);
        }
        transaction.commit();
    }
    
    /**
     * Sets the birth date of a director.
     *
     * @param elements the elements of the director CSV line
     * @param director the director entity
     */
    private static void setDirectorBirthDate(String[] elements, Director director) {
        if (!elements[2].isEmpty()) {
            try {
                LocalDate result = LocalDate.parse(elements[2].trim(), DateTimeFormatter.ofPattern("MMMM d yyyy", Locale.ENGLISH));
                director.setBirthDate(result);
            } catch (DateTimeParseException e) {
            	 // Gestion de l'erreur
                System.err.println("Erreur de parsing de la date pour l'élément : " + elements[2]);
            }
        }
    }

}
