// -----------------------------------------------------
// Assignment 2 - COMP 249
// Written by: Maëlys Arnaud 40278798
// Due date : March 27, 2024
// -----------------------------------------------------
package SementicError;

public class MissingScoreException extends Exception{

	private static final long serialVersionUID = 1L;
	public MissingScoreException(){
        super("SementicError: Missing Score");
    }
    public MissingScoreException(String message){
        super(message);
    }
}
