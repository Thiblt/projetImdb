package service;

import java.util.List;
import java.util.Set;

import entity.Actor;
import entity.Movie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public class AccessData {
	
	public static void getFilmFromActor(String actor) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("configBdd");
		EntityManager em = entityManagerFactory.createEntityManager();
		
		TypedQuery<Actor> query = em.createQuery("SELECT a FROM actor a where a.identity =:libelle", Actor.class);
		query.setParameter("libelle", actor);
		List<Actor> actors = query.getResultList();
		if(actors.isEmpty()) {
			System.out.println("Aucun acteur trouvé");
		}
		else {
			Set<Movie> filmList= actors.get(0).getMovies();
			for(Movie film: filmList) {
				System.out.println(film.getName());
				
			}
		}
	}
	
	public static void getCastingFromFilm(String movie) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("configBdd");
		EntityManager em = entityManagerFactory.createEntityManager();
		
		TypedQuery<Movie> query = em.createQuery("SELECT m FROM movie m where m.name =:libelle", Movie.class);
		query.setParameter("libelle", movie);
		List<Movie> movies = query.getResultList();
		if(movies.isEmpty()) {
			System.out.println("Aucun film trouvé");
		}
		else {
			Set<Actor> actorList= movies.get(0).getActors();
			for(Actor actor: actorList) {
				System.out.println(actor.getIdentity());
				
			}
		}
	}
	
	public static void getFilmFromYears(int yearBeg, int yearEnd) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("configBdd");
		EntityManager em = entityManagerFactory.createEntityManager();
		
		TypedQuery<Movie> query = em.createQuery("SELECT m FROM Movie m WHERE m.releaseYear BETWEEN :startYear AND :endYear", Movie.class);
		query.setParameter("startYear", yearBeg);
		query.setParameter("endYear", yearEnd);
		
		List<Movie> movies = query.getResultList();
		if(movies.isEmpty()) {
			System.out.println("Aucun film trouvé");
		}
		else {
			for(Movie movie: movies) {
				System.out.println(movie.getName());
				
			}
		}
	}
	
	 // Requête pour sélectionner les films communs à deux acteurs donnés
	public static void getCommonFilms(String actor1, String actor2) {
	    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("configBdd");
	    EntityManager em = entityManagerFactory.createEntityManager();
	   
	    TypedQuery<Movie> query = em.createQuery(
	        "SELECT m FROM Movie m " +
	        "JOIN m.actors a1 " +
	        "JOIN m.actors a2 " +
	        "WHERE a1.identity = :actor1 " +
	        "AND a2.identity = :actor2", Movie.class);

	    query.setParameter("actor1", actor1);
	    query.setParameter("actor2", actor2);

	    List<Movie> movies = query.getResultList();
	    if(movies.isEmpty()) {
	        System.out.println("Aucun film commun trouvé pour les acteurs/actrices donnés.");
	    } else {
	        System.out.println("Films communs pour les acteurs/actrices :");
	        for(Movie movie: movies) {
	            System.out.println(movie.getName());
	        }
	    }
	}
	
	// Requête pour sélectionner les acteurs communs à deux films donnés
	public static void getCommonActors(String movie1, String movie2) {
	    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("configBdd");
	    EntityManager em = entityManagerFactory.createEntityManager();
	    
	    TypedQuery<Actor> query = em.createQuery(
	        "SELECT a FROM Actor a " +
	        "JOIN a.movies m1 " +
	        "JOIN a.movies m2 " +
	        "WHERE m1.name = :movie1 " +
	        "AND m2.name = :movie2", Actor.class);

	    query.setParameter("movie1", movie1);
	    query.setParameter("movie2", movie2);

	    List<Actor> actors = query.getResultList();
	    if(actors.isEmpty()) {
	        System.out.println("Aucun acteur commun trouvé pour les films donnés.");
	    } else {
	        System.out.println("Acteurs communs pour les films donnés :");
	        for(Actor actor: actors) {
	            System.out.println(actor.getIdentity());
	        }
	    }
	}
	
	 // Requête pour sélectionner les films sortis entre deux années données avec un acteur donné au casting
	public static void getMoviesByYearAndActor(int startYear, int endYear, String actor) {
	    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("configBdd");
	    EntityManager em = entityManagerFactory.createEntityManager();

	    TypedQuery<Movie> query = em.createQuery(
	        "SELECT DISTINCT m FROM Movie m " +
	        "JOIN m.actors a " +
	        "WHERE m.releaseYear BETWEEN :startYear AND :endYear " +
	        "AND a.identity = :actor", Movie.class);

	    query.setParameter("startYear", startYear);
	    query.setParameter("endYear", endYear);
	    query.setParameter("actor", actor);

	    List<Movie> movies = query.getResultList();
	    if(movies.isEmpty()) {
	        System.out.println("Aucun film trouvé pour les critères donnés.");
	    } else {
	        System.out.println("Films sortis entre " + startYear + " et " + endYear +
	                           " avec l'acteur/actrice" + actor + " :");
	        for(Movie movie : movies) {
	            System.out.println(movie.getName());
	        }
	    }
	}

}
