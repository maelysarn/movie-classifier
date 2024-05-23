// -----------------------------------------------------
// Assignment 2 - COMP 249
// Written by: Maëlys Arnaud 40278798
// Due date : March 27, 2024
// -----------------------------------------------------
package SementicError;

public class InvalidScoreException extends Exception{
	private static final long serialVersionUID = 1L; // because exceptions needs to be serialized 
	public InvalidScoreException(){
        super("SementicError: Invalid Score");
    }
    public InvalidScoreException(String message){
        super(message);
    }
}
