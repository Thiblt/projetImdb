package service.data;

import java.util.List;

import entity.Country;
import entity.Movie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

public class CountryService {
	
	/**
     * Saves countries to the database.
     *
     * @param lines the lines of the countries CSV file
     * @param em the entity manager to persist data and create transaction
     */
    public static void saveCountries(List<String> lines, EntityManager em ) {
    	EntityTransaction transaction= em.getTransaction();
        transaction.begin();
        for (int i = 1; i < lines.size(); i++) {
            String[] elements = lines.get(i).split(";", -1);
            Country country = new Country(elements[0].toLowerCase(), elements[1]);
            em.persist(country);
        }
        transaction.commit();
    }
    
    /**
     * Saves countries on movie and to the database if necessary.
     *
     * @param country the countries from the CSV file
     * @param em the entity manager to persist data and create transaction
     */
    public static void saveCountriesOnMovie(String country,  Movie m , EntityManager em) {
    if(!(country.equals("") || country.length()>50)) {
		
		TypedQuery<Country> query = em.createQuery("SELECT c FROM Country c where c.country =:libelle", Country.class);
		query.setParameter("libelle", country.toLowerCase().toLowerCase());
		List<Country> countries = query.getResultList();
		if(countries.isEmpty()) {
			Country c= new Country();
			c.setCountry(country.toLowerCase());
			em.persist(c);
			m.setMovieCountry(c);
		}
		else {
			m.setMovieCountry(countries.get(0));
		}
    }
    }

}
