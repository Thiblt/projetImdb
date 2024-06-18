package entity;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Location {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;
	@Column(name = "city", length = 100)
	private String city;
	@Column(name = "state_dpt", length = 50)
	private String stateDpt;
	private String additionnalAdress;
	
	@ManyToOne
	@JoinColumn(name="country_id")
	private Country country;
	
	@OneToMany(mappedBy="birthLocationActor")
	private Set<Actor> acteurs;
	
	@OneToMany(mappedBy="birthLocationDirector")
	private Set<Director> directeurs;
	
	@OneToMany(mappedBy="shootingLocation")
	private Set<Movie> movies;

	public Location(String city, String stateDpt, String additionnalAdress) {
		this.city = city;
		this.stateDpt = stateDpt;
		this.additionnalAdress = additionnalAdress;
	}
	public Location() {}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getStateDpt() {
		return stateDpt;
	}
	public void setStateDpt(String stateDpt) {
		this.stateDpt = stateDpt;
	}
	public String getAdditionnalAdress() {
		return additionnalAdress;
	}
	public void setAdditionnalAdress(String additionnalAdress) {
		this.additionnalAdress = additionnalAdress;
	}
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
	}
	public Set<Actor> getActeurs() {
		return acteurs;
	}
	public void setActeurs(Set<Actor> acteurs) {
		this.acteurs = acteurs;
	}
	public Set<Director> getDirecteurs() {
		return directeurs;
	}
	public void setDirecteurs(Set<Director> directeurs) {
		this.directeurs = directeurs;
	}
	public Set<Movie> getMovies() {
		return movies;
	}
	public void setMovies(Set<Movie> movies) {
		this.movies = movies;
	}
	
	

}
