package service.data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

import entity.Genre;
import entity.Language;
import entity.Movie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

public class MovieService {
	/**
     * Saves movies to the database.
     *
     * @param lines the lines of the movies CSV file
     */
    public static void saveMovies(List<String> lines ,EntityManager em ) {
    	EntityTransaction transaction= em.getTransaction(); 
        transaction.begin();
        for (int i = 1; i < lines.size(); i++) {
            String[] elements = lines.get(i).split(";", -1);
            Movie m = new Movie();
            m.setName(elements[1]);
			m.setIdImdb(elements[0]);
			m.setUrl(elements[4]);
			if(!elements[3].isEmpty()) {
				m.setRating(Double.parseDouble(elements[3].replace(",",".")));
			}
			m.setPlot(elements[8]);
			LocationService.setMovieLocation(elements, m, em);
            setMovieReleaseYear(elements, m);
            setMovieLanguages(elements, m, em);
            setMovieGenres(elements, m, em);
            CountryService.saveCountriesOnMovie(elements[9], m, em);
            em.persist(m);
        }
        transaction.commit();
    }
    /**
     * Sets the release date of a movie.
     *
     * @param elements the elements of the movie CSV line
     * @param m the movie entity
     */
    private static void setMovieReleaseYear(String[] elements, Movie m) {
        if (!elements[2].isEmpty()) {
        	try {
				m.setReleaseYear(Integer.parseInt(elements[2]));
				} catch (NumberFormatException e) {
				    // Gestion de l'erreur de conversion en int
				    System.err.println("Erreur de conversion en int pour la date : " + elements[2]);  
				}
        }
    }
    
    /**
     * Sets the languages of a movie.
     *
     * @param elements the elements of the movie CSV line
     * @param movie the movie entity
     */
    private static void setMovieLanguages(String[] elements, Movie m ,EntityManager em ) {
    	if(!(elements[7].equals(""))) {
			
			TypedQuery<Language> query = em.createQuery("SELECT l FROM Language l where l.name =:libelle", Language.class);
			query.setParameter("libelle", elements[7].toLowerCase());
			List<Language> languagebdd = query.getResultList();
			if(languagebdd.isEmpty()) {
				Language le= new Language();
				le.setName(elements[7].toLowerCase());
				em.persist(le);
				m.setLanguage(le);
			}
			else {
				m.setLanguage(languagebdd.get(0));
			}
    }
    	
    	

}
    /**
     * Sets the genres of a movie.
     *
     * @param elements the elements of the movie CSV line
     * @param movie the movie entity
     */
    private static void setMovieGenres(String[] elements, Movie m, EntityManager em ) {
    	if(!(elements[6].equals(""))) {
			String[] genres = elements[6].split(",", -1);
			HashSet<Genre> genreAdd= new HashSet<Genre>();
			for (int g=0; g<genres.length; g++) {
				
				
				TypedQuery<Genre> query = em.createQuery("SELECT g FROM Genre g where g.name =:libelle", Genre.class);
				query.setParameter("libelle", genres[g].toLowerCase());
				List<Genre> genrebdd = query.getResultList();
				if(genrebdd.isEmpty()) {
					Genre gen= new Genre();
					gen.setName(genres[g].toLowerCase());
					em.persist(gen);
					genreAdd.add(gen);
				}
				else {
					genreAdd.add(genrebdd.get(0));
				}
				
			}
			m.setGenres(genreAdd);
    }
    }
}
