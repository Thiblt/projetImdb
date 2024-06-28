package service.data;

import java.util.List;
import java.util.Set;

import entity.Actor;
import entity.Director;
import entity.Movie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

public class DataLinkedService {
	
	/**
     * Link actors and movies on the database.
     *
     * @param lines the lines of the actors/movie CSV file
     * @param em the entity manager to persist data and create transaction
     */
	 public static void LinkActorsAndMovies(List<String> lines,EntityManager em ) {
	    	EntityTransaction transaction= em.getTransaction();
	transaction.begin();
	for(int i =1; i<lines.size(); i++) {

		
		String[] elements = lines.get(i).split(";", -1);
		
			
			TypedQuery<Movie> query = em.createQuery("SELECT m FROM Movie m where m.idImdb =:libelle", Movie.class);
			query.setParameter("libelle", elements[0]);
			List<Movie> movie = query.getResultList();
			TypedQuery<Actor> queryActor = em.createQuery("SELECT a FROM Actor a where a.idImdb =:libelle", Actor.class);
			queryActor.setParameter("libelle", elements[1]);
			List<Actor> actor = queryActor.getResultList();
			if(!movie.isEmpty() && !actor.isEmpty()) {
				Set<Actor> actors= movie.get(0).getActors();
				actors.add(actor.get(0));
				movie.get(0).setActors(actors);
			}
	}
	transaction.commit();
	 }
	 
	 /**
	     * Link actors and movies on the database.
	     *
	     * @param lines the lines of the director/movie CSV file
	     * @param em the entity manager to persist data and create transaction
	     */
	 public static void LinkDirectorAndMovies(List<String> lines,EntityManager em ) {
	    	EntityTransaction transaction= em.getTransaction();
	 transaction.begin();
		for(int i =1; i<lines.size(); i++) {

			
			String[] elements = lines.get(i).split(";", -1);
			
				
				TypedQuery<Movie> query = em.createQuery("SELECT m FROM Movie m where m.idImdb =:libelle", Movie.class);
				query.setParameter("libelle", elements[0]);
				List<Movie> movie = query.getResultList();
				TypedQuery<Director> queryDirector = em.createQuery("SELECT d FROM Director d where d.idImdb =:libelle", Director.class);
				queryDirector.setParameter("libelle", elements[1]);
				List<Director> director = queryDirector.getResultList();
				if(!movie.isEmpty() && !director.isEmpty()) {
					movie.get(0).setDirector(director.get(0));
				}
		}
		transaction.commit();
	 }

}
