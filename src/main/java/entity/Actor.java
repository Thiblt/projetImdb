package entity;

import java.time.LocalDate;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Actor extends Person{
	private double height;
	
	@ManyToOne
	@JoinColumn(name="birth_place")
	private Location birthLocationActor;
	
	@OneToMany(mappedBy="actor")
	private Set<Role> roles;
	
	@ManyToMany
	@JoinTable(name="main_cast",
	joinColumns=@JoinColumn(name="actor_id", referencedColumnName="id"),
	inverseJoinColumns=@JoinColumn(name="movie_id", referencedColumnName="id")
	)
	private Set<Movie> movies;

	public Actor() {}

	public Actor( String idImdb, String identity, LocalDate birthDate, String url, double height) {
		super(idImdb, identity, birthDate, url);
		this.height = height;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public Location getBirthLocationActor() {
		return birthLocationActor;
	}

	public void setBirthLocationActor(Location birthLocationActor) {
		this.birthLocationActor = birthLocationActor;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Set<Movie> getMovies() {
		return movies;
	}

	public void setMovies(Set<Movie> movies) {
		this.movies = movies;
	}
	
	
	
	
	

}
