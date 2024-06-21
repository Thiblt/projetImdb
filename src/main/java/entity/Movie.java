package entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Movie {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;
	@Column(name = "name", length = 255, nullable = false)
	private String name;
	@Column(name = "url", length = 255, nullable = false)
	private String url;
	@Column(name = "release_year")
	private int releaseYear;
	@Column(name = "id_imdb", length = 50, nullable = false)
	private String idImdb;
	private double rating;
	@Column(length = 1000)
	private String plot;
	
	@ManyToOne
	@JoinColumn(name="country_id")
	private Country movieCountry;
	@ManyToOne
	@JoinColumn(name="shooting_location")
	private Location shootingLocation;
	@ManyToOne
	@JoinColumn(name="director_id")
	private Director director;
	@ManyToOne
	@JoinColumn(name="language_id")
	private Language language;
	
	@OneToMany(mappedBy="movie")
	private Set<Role> roles;
	
	@ManyToMany
	@JoinTable(name="movie_genre",
	joinColumns=@JoinColumn(name="movie_id", referencedColumnName="id"),
	inverseJoinColumns=@JoinColumn(name="genre_id", referencedColumnName="id")
	)
	private Set<Genre> genres;
	
	@ManyToMany
	@JoinTable(name="main_cast",
	joinColumns=@JoinColumn(name="movie_id", referencedColumnName="id"),
	inverseJoinColumns=@JoinColumn(name="actor_id", referencedColumnName="id")
	)
	private Set<Actor> actors= new HashSet();

	public Movie(String name, String url, int releaseYear, String idImdb, double rating, String plot) {
		this.name = name;
		this.url = url;
		this.releaseYear = releaseYear;
		this.idImdb = idImdb;
		this.rating = rating;
		this.plot = plot;
	}
	
	public Movie() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(int releaseYear) {
		this.releaseYear = releaseYear;
	}

	public String getIdImdb() {
		return idImdb;
	}

	public void setIdImdb(String idImdb) {
		this.idImdb = idImdb;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public String getPlot() {
		return plot;
	}

	public void setPlot(String plot) {
		this.plot = plot;
	}

	public Country getMovieCountry() {
		return movieCountry;
	}

	public void setMovieCountry(Country movieCountry) {
		this.movieCountry = movieCountry;
	}

	public Location getShootingLocation() {
		return shootingLocation;
	}

	public void setShootingLocation(Location shootingLocation) {
		this.shootingLocation = shootingLocation;
	}

	public Director getDirector() {
		return director;
	}

	public void setDirector(Director director) {
		this.director = director;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Set<Genre> getGenres() {
		return genres;
	}

	public void setGenres(Set<Genre> genres) {
		this.genres = genres;
	}

	public Set<Actor> getActors() {
		return actors;
	}

	public void setActors(Set<Actor> actors) {
		this.actors = actors;
	}
	
	

}
