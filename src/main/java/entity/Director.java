package entity;

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Director extends Person{
	
	@ManyToOne
	@JoinColumn(name="birth_place")
	private Location birthLocationDirector;
	
	@OneToMany(mappedBy="director")
	private Set<Movie> movies;

	public Director() {
	}

	public Director(String idImdb, String identity, LocalDate birthDate, String url) {
		super(idImdb, identity, birthDate, url);
	}

	public Location getBirthLocation() {
		return birthLocationDirector;
	}

	public void setBirthLocation(Location birthLocationDirector) {
		this.birthLocationDirector = birthLocationDirector;
	}

	public Set<Movie> getMovies() {
		return movies;
	}

	public void setMovies(Set<Movie> movies) {
		this.movies = movies;
	}
	
	
	

}
