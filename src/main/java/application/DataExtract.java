package application;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import entity.Actor;
import entity.Country;
import entity.Director;
import entity.Genre;
import entity.Language;
import entity.Location;
import entity.Movie;
import entity.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public class DataExtract {

	public static void main(String[] args) throws IOException {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("configBdd");
		EntityManager em = entityManagerFactory.createEntityManager();
		EntityTransaction transaction= em.getTransaction();
		
		Path actorsPath= Paths.get("C:/Data/Info/CDA/ToutLesCours/ProjetCsvJpa/acteurs.csv");
		List<String> actorLines=Files.readAllLines(actorsPath);
		Path castingPath= Paths.get("C:/Data/Info/CDA/ToutLesCours/ProjetCsvJpa/castingPrincipal.csv");
		List<String> castingLines=Files.readAllLines(castingPath);
		Path filmRealPath= Paths.get("C:/Data/Info/CDA/ToutLesCours/ProjetCsvJpa/film_realisateurs.csv");
		List<String> filmRealLines=Files.readAllLines(filmRealPath);
		Path filmsPath= Paths.get("C:/Data/Info/CDA/ToutLesCours/ProjetCsvJpa/films.csv");
		List<String> filmsLines=Files.readAllLines(filmsPath);
		Path paysPath= Paths.get("C:/Data/Info/CDA/ToutLesCours/ProjetCsvJpa/pays.csv");
		List<String> paysLines=Files.readAllLines(paysPath);
		Path realPath= Paths.get("C:/Data/Info/CDA/ToutLesCours/ProjetCsvJpa/realisateurs.csv");
		List<String> realLines=Files.readAllLines(realPath);
		Path rolesPath= Paths.get("C:/Data/Info/CDA/ToutLesCours/ProjetCsvJpa/roles.csv");
		List<String> rolesLines=Files.readAllLines(rolesPath);
		
		
		//debut transaction pour les pays
		transaction.begin();
		for(int i =1; i<paysLines.size(); i++) {
			
			
			
			
			String[] elements = paysLines.get(i).split(";", -1);
			Country c= new Country(elements[0].toLowerCase(), elements[1]);
			em.persist(c);
		}
		transaction.commit();
		
		
		//debut transaction pour les acteurs
		transaction.begin();
		for(int i =1; i<2; i++) {
			
			
			
			
			String[] elements = actorLines.get(i).split(";", -1);
			if(elements.length==7) {
			Actor a= new Actor();
			
			if(!(elements[3].isEmpty())) {
			String[] birthplace = elements[3].split(",", -1);
				Location l = new Location();
				
				TypedQuery<Country> query = em.createQuery("SELECT c FROM Country c where c.country =:libelle", Country.class);
				query.setParameter("libelle", birthplace[birthplace.length-1].toLowerCase());
				List<Country> countries = query.getResultList();
				if(countries.isEmpty()) {
					Country c= new Country();
					c.setCountry(elements[0].toLowerCase());
					em.persist(c);
					l.setCountry(c);
				}
				else {
					l.setCountry(countries.get(0));
				}
				if(birthplace.length>1) {
					l.setCity(birthplace[birthplace.length-2].toLowerCase());
				}
				
				
				
				if(birthplace.length ==4) {
					l.setAdditionnalAdress(birthplace[0]+", " + birthplace[1]);
				}
				else {
					l.setAdditionnalAdress(birthplace[0]);
				}
				em.persist(l);
				a.setBirthLocationActor(l);
			}
				 
			
			if(!elements[2].isEmpty()) {
				 try {
				        LocalDate result = LocalDate.parse(elements[2].trim(), DateTimeFormatter.ofPattern("MMMM d yyyy", Locale.ENGLISH));
				        a.setBirthDate(result);
				    } catch (DateTimeParseException e) {
				        // Gestion de l'erreur
				        System.err.println("Erreur de parsing de la date pour l'élément : " + elements[2]);
				    }
			}
			try {
			if(!elements[4].isEmpty()) {
				a.setHeight(Double.parseDouble(elements[4].replace("m", "").replace(",",".").replace("\u202F", " ")));
			}
			} catch (NumberFormatException e) {
			    // Gestion de l'erreur de conversion en double
			    System.err.println("Erreur de conversion en double pour la hauteur : " + elements[4]);
			    
			}
			
			
			a.setIdentity(elements[1]);
			a.setIdImdb(elements[0]);
			a.setUrl(elements[5]);
			em.persist(a);
		}
		}
		transaction.commit();
		
		
		//debut transaction pour les realisateurs
		transaction.begin();
		for(int i =1; i<2; i++) {
			
			
			
			
			String[] elements = realLines.get(i).split(";", -1);
			Director d= new Director();
			
			if(!(elements[3].equals(""))) {
			String[] birthplace = elements[3].split(",", -1);
				Location l = new Location();
				
				TypedQuery<Country> query = em.createQuery("SELECT c FROM Country c where c.country =:libelle", Country.class);
				query.setParameter("libelle", birthplace[birthplace.length-1].toLowerCase());
				List<Country> countries = query.getResultList();
				if(countries.isEmpty()) {
					Country c= new Country();
					c.setCountry(elements[0].toLowerCase());
					em.persist(c);
					l.setCountry(c);
				}
				else {
					l.setCountry(countries.get(0));
				}
				
				if(birthplace.length>1) {
					l.setCity(birthplace[birthplace.length-2].toLowerCase());
				}
				
				if(birthplace.length ==4) {
					l.setAdditionnalAdress(birthplace[0]+", " + birthplace[1]);
				}
				else {
					l.setAdditionnalAdress(birthplace[0]);
				}
				em.persist(l);
				d.setBirthLocationDirector(l);
			}
				 
			
			if(!elements[2].isEmpty()) {
				 try {
				        LocalDate result = LocalDate.parse(elements[2].trim(), DateTimeFormatter.ofPattern("MMMM d yyyy", Locale.ENGLISH));
				        d.setBirthDate(result);
				    } catch (DateTimeParseException e) {
				        // Gestion de l'erreur
				        System.err.println("Erreur de parsing de la date pour l'élément : " + elements[2]);
				  
				    }
			}
			d.setIdentity(elements[1]);
			d.setIdImdb(elements[0]);
			d.setUrl(elements[4]);
			em.persist(d);   
		}
		transaction.commit();
		
		//debut transaction pour les films
		transaction.begin();
		for(int i =1; i<filmsLines.size(); i++) {
			
			
			
			
			String[] elements = filmsLines.get(i).split(";", -1);
			Movie m= new Movie();
			
			if(!(elements[5].equals(""))) {
			String[] shootingLocation = elements[5].split(",", -1);
				Location l = new Location();
				
				TypedQuery<Country> query = em.createQuery("SELECT c FROM Country c where c.country =:libelle", Country.class);
				query.setParameter("libelle", shootingLocation[0].toLowerCase().toLowerCase());
				List<Country> countries = query.getResultList();
				if(countries.isEmpty()) {
					Country c= new Country();
					c.setCountry(shootingLocation[0].toLowerCase());
					em.persist(c);
					l.setCountry(c);
				}
				else {
					l.setCountry(countries.get(0));
				}
				
				l.setCity(shootingLocation[1].toLowerCase());
				
				if(shootingLocation.length ==4) {
					l.setAdditionnalAdress(shootingLocation[2]+", " + shootingLocation[3]);
				}
				if(shootingLocation.length ==3) {
					l.setAdditionnalAdress(shootingLocation[2]);
				}
				em.persist(l);
				m.setShootingLocation(l);
			}
			
			//Boucle gestion Genre
			
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
			//gestion langue
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
			//gestion Pays
			if(!(elements[9].equals("") || elements[9].length()>50)) {
				
				TypedQuery<Country> query = em.createQuery("SELECT c FROM Country c where c.country =:libelle", Country.class);
				query.setParameter("libelle", elements[9].toLowerCase().toLowerCase());
				List<Country> countries = query.getResultList();
				if(countries.isEmpty()) {
					Country c= new Country();
					c.setCountry(elements[9].toLowerCase());
					em.persist(c);
					m.setMovieCountry(c);
				}
				else {
					m.setMovieCountry(countries.get(0));
				}
					
				
			}
				 
			try {
				m.setReleaseYear(Integer.parseInt(elements[2]));
				} catch (NumberFormatException e) {
				    // Gestion de l'erreur de conversion en int
				    System.err.println("Erreur de conversion en int pour la date : " + elements[2]);  
				}
			m.setName(elements[1]);
			m.setIdImdb(elements[0]);
			m.setUrl(elements[4]);
			if(!elements[3].isEmpty()) {
				m.setRating(Double.parseDouble(elements[3].replace(",",".")));
			}
			m.setPlot(elements[8]);
			em.persist(m);   
		}
		transaction.commit();
		
		
				
				//debut transaction pour le casting principal
				transaction.begin();
				for(int i =1; i<castingLines.size(); i++) {

					
					String[] elements = castingLines.get(i).split(";", -1);
					
						
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
				
				
				
				
				//debut transaction pour les realisateurs/films
				transaction.begin();
				for(int i =1; i<filmRealLines.size(); i++) {

					
					String[] elements = filmRealLines.get(i).split(";", -1);
					
						
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
				
				//debut transaction pour les roles
				transaction.begin();
				for(int i =1; i<rolesLines.size(); i++) {

					
					String[] elements = rolesLines.get(i).split(";", -1);
					Role r = new Role();
					r.setCharacterName(elements[2]);
					
						
						TypedQuery<Movie> query = em.createQuery("SELECT m FROM Movie m where m.idImdb =:libelle", Movie.class);
						query.setParameter("libelle", elements[0]);
						List<Movie> movie = query.getResultList();
						TypedQuery<Actor> queryActor = em.createQuery("SELECT a FROM actor a where a.idImdb =:libelle", Actor.class);
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
