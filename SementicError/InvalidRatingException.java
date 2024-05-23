// -----------------------------------------------------
// Assignment 2 - COMP 249
// Written by: Maëlys Arnaud 40278798
// Due date : March 27, 2024
// -----------------------------------------------------
package SementicError;

public class InvalidRatingException extends Exception{
	private static final long serialVersionUID = 1L;
	public InvalidRatingException(){
        super("SementicError: Invalid Rating");
    }
    public InvalidRatingException(String message){
        super(message);
    }
}
