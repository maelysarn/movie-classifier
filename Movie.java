// -----------------------------------------------------
// Assignment 2 - COMP 249
// Written by: Maëlys Arnaud 40278798
// Due date : March 27, 2024
// -----------------------------------------------------
import java.io.Serializable;

public class Movie implements Serializable{

	
	private static final long serialVersionUID = 1L; 
	
	private int year;
	private String title;
	private int duration; // in minutes
	private String genre;
	private String rating;
	private double score;
	private String director;
	private String actor1;
	private String actor2;
	private String actor3;
	
	public Movie (int year, String title, int duration, String genre, String rating, double score, String director, String actor1, String actor2, String actor3) {
		this.year = year;
		this.title = title;
		this.duration = duration;
		this.genre = genre;
		this.rating = rating;
		this.score = score;
		this.director = director;
		this.actor1 = actor1;
		this.actor2 = actor2;
		this.actor3 = actor3;
	}
	
	public int getYear() {
        return this.year;
	}
	public String getTitle() {
        return this.title;
	}
	public int getDuration() {
        return this.duration;
	}
	public String getGenre() {
        return this.genre;
	}
	public String getRating() {
        return this.rating;
	}
	public double getScore() {
        return this.score;
	}
	public String getDirector() {
        return this.director;
	}
	public String getActor1() {
        return this.actor1;
	}
	public String getActor2() {
        return this.actor2;
	}
	public String getActor3() {
        return this.actor3;
	}
	
	// setters 
	public void setYear(int year) {
        this.year = year;
	}
	public void setTitle(String title) {
        this.title = title;
	}
	public void setDuration(int duration) {
        this.duration = duration;
	}
	public void setGenre(String genre) {
        this.genre = genre;
	}
	public void setRating(String rating) {
        this.rating = rating;
	}
	public void getScore(double score) {
        this.score = score;
	}
	public void setDirector(String director) {
        this.director = director;
	}
	public void setActor1(String actor1) {
        this.actor1 = actor1;
	}
	public void setActor2(String actor2) {
        this.actor2 = actor2;
	}
	public void setActor3(String actor3) {
        this.actor3 = actor3;
	}
	
	@Override 
	public boolean equals(Object other) {
        if (other == null)
        return false;
		else if (this.getClass() != other.getClass())
        return false;
		else {
            Movie otherMovie = (Movie) other; // downcast to use the getters of the Movie class
			return (this.year == otherMovie.getYear() && this.title.equals(otherMovie.getTitle())
            && this.duration == otherMovie.getDuration() && this.genre.equals(otherMovie.getGenre())
            && this.rating.equals(otherMovie.getRating()) && this.score == otherMovie.getScore()
            && this.director.equals(otherMovie.getDirector()) && this.actor1.equals(otherMovie.getActor1())
            && this.actor2.equals(otherMovie.getActor2()) && this.actor3.equals(otherMovie.getActor3()));
		}
	}
	
	@Override
	public String toString() {
        return year + "," + title + "," + duration + "," + genre + "," + rating + "," 
        + score + "," + director + "," + actor1 + "," + actor2 + "," + actor3;
	}
	
}

