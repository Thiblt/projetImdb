 package entity;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
public class Genre {
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private int id;
	@Column(name = "name", length = 50, nullable = false, unique = true)
	private String name;
	
	@ManyToMany
	@JoinTable(name="movie_genre",
	joinColumns=@JoinColumn(name="genre_id", referencedColumnName="id"),
	inverseJoinColumns=@JoinColumn(name="movie_id", referencedColumnName="id")
	)
	private Set<Movie> movies;

	public Genre(String name) {
		this.name = name;
	}
	public Genre() {}
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
	public Set<Movie> getMovies() {
		return movies;
	}
	public void setMovies(Set<Movie> movies) {
		this.movies = movies;
	}
	
	

}
