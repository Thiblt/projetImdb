package service.data;

import java.util.List;

import entity.Actor;
import entity.Country;
import entity.Director;
import entity.Location;
import entity.Movie;
import entity.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class LocationService {

	/**
	 * Sets the birth location of an actor.
	 *
	 * @param elements the elements of the actor CSV line
	 * @param person   the person entity
	 */
	public static void setPersonLocation(String[] elements, Person person, EntityManager em) {
		if (!elements[3].isEmpty()) {
			String[] birthplace = elements[3].split(",", -1);
			Location location = new Location();
			if ((birthplace[birthplace.length - 1]).length() < 50) {
				TypedQuery<Country> query = em.createQuery("SELECT c FROM Country c WHERE c.country = :libelle",
						Country.class);
				query.setParameter("libelle", birthplace[birthplace.length - 1].toLowerCase());
				List<Country> countries = query.getResultList();
				if (countries.isEmpty()) {
					Country country = new Country();
					country.setCountry(birthplace[birthplace.length - 1].toLowerCase());
					em.persist(country);
					location.setCountry(country);
				} else {
					location.setCountry(countries.get(0));
				}
				if (birthplace.length > 1) {
					location.setCity(birthplace[birthplace.length - 2].toLowerCase());
				}
				if (birthplace.length == 4) {
					location.setAdditionnalAdress(birthplace[0] + ", " + birthplace[1]);
				} else {
					location.setAdditionnalAdress(birthplace[0]);
				}
				em.persist(location);
				if (person.getClass() == Director.class) {
					((Director) person).setBirthLocation(location);

				} else if (person.getClass() == Actor.class) {
					((Actor) person).setBirthLocation(location);
				}
			}
		}
	}

	/**
	 * Sets the shooting location of a movie.
	 *
	 * @param elements the elements of the movie CSV line
	 * @param m        the movie entity
	 */
	public static void setMovieLocation(String[] elements, Movie m, EntityManager em) {
		if (!(elements[5].equals(""))) {
			String[] shootingLocation = elements[5].split(",", -1);
			Location l = new Location();

			TypedQuery<Country> query = em.createQuery("SELECT c FROM Country c where c.country =:libelle",
					Country.class);
			query.setParameter("libelle", shootingLocation[0].toLowerCase().toLowerCase());
			List<Country> countries = query.getResultList();
			if (countries.isEmpty()) {
				Country c = new Country();
				c.setCountry(shootingLocation[0].toLowerCase());
				em.persist(c);
				l.setCountry(c);
			} else {
				l.setCountry(countries.get(0));
			}

			l.setCity(shootingLocation[1].toLowerCase());

			if (shootingLocation.length == 4) {
				l.setAdditionnalAdress(shootingLocation[2] + ", " + shootingLocation[3]);
			}
			if (shootingLocation.length == 3) {
				l.setAdditionnalAdress(shootingLocation[2]);
			}
			em.persist(l);
			m.setShootingLocation(l);
		}
	}

}
