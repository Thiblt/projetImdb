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
import service.CsvService;
import service.data.ActorService;
import service.data.CountryService;
import service.data.DataLinkedService;
import service.data.DirectorService;
import service.data.MovieService;
import service.data.RoleService;

public class DataExtract {

	 /**
     * Extract Data from CSV and save them on DB with some methods
     *
     */
	public static void main(String[] args) throws IOException {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("configBdd");
		EntityManager em = entityManagerFactory.createEntityManager();
		EntityTransaction transaction= em.getTransaction();
        
        // Load data from CSV files
        List<String> actorLines = CsvService.readFile("C:/Data/Info/CDA/ToutLesCours/ProjetCsvJpa/acteurs.csv");
        List<String> castingLines = CsvService.readFile("C:/Data/Info/CDA/ToutLesCours/ProjetCsvJpa/castingPrincipal.csv");
        List<String> filmRealLines = CsvService.readFile("C:/Data/Info/CDA/ToutLesCours/ProjetCsvJpa/film_realisateurs.csv");
        List<String> filmsLines = CsvService.readFile("C:/Data/Info/CDA/ToutLesCours/ProjetCsvJpa/films.csv");
        List<String> paysLines = CsvService.readFile("C:/Data/Info/CDA/ToutLesCours/ProjetCsvJpa/pays.csv");
        List<String> realLines = CsvService.readFile("C:/Data/Info/CDA/ToutLesCours/ProjetCsvJpa/realisateurs.csv");
        List<String> rolesLines = CsvService.readFile("C:/Data/Info/CDA/ToutLesCours/ProjetCsvJpa/roles.csv");
        
        // Process and save data on DB
        CountryService.saveCountries(paysLines, em);
        ActorService.saveActors(actorLines, em);
        DirectorService.saveDirectors(realLines, em);
        MovieService.saveMovies(filmsLines, em);
        DataLinkedService.LinkActorsAndMovies(castingLines, em);
        DataLinkedService.LinkDirectorAndMovies(filmRealLines, em);
        RoleService.LinkDirectorAndMovies(rolesLines, em);
	}

}
