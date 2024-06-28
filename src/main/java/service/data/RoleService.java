package service.data;

import java.util.List;

import entity.Actor;
import entity.Movie;
import entity.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

public class RoleService {
	/**
     * save role on the database.
     *
     * @param lines the lines of the roles CSV file
     * @param em the entity manager to persist data and create transaction
     */
 public static void LinkDirectorAndMovies(List<String> lines,EntityManager em ) {
	 EntityTransaction transaction= em.getTransaction();
	transaction.begin();
	for(int i =1; i<lines.size(); i++) {

		
		String[] elements = lines.get(i).split(";", -1);
		Role r = new Role();
		r.setCharacterName(elements[2]);
		
			
			TypedQuery<Movie> query = em.createQuery("SELECT m FROM Movie m where m.idImdb =:libelle", Movie.class);
			query.setParameter("libelle", elements[0]);
			List<Movie> movie = query.getResultList();
			TypedQuery<Actor> queryActor = em.createQuery("SELECT a FROM Actor a where a.idImdb =:libelle", Actor.class);
			queryActor.setParameter("libelle", elements[1]);
			List<Actor> actor = queryActor.getResultList();
			if(!movie.isEmpty() && !actor.isEmpty()) {
				r.setMovie(movie.get(0));
				r.setActor(actor.get(0));
				em.persist(r);
			}
	}
	transaction.commit();
 }

}
