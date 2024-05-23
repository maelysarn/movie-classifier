// -----------------------------------------------------
// Assignment 2 - COMP 249
// Written by: Maëlys Arnaud 40278798
// Due date : March 27, 2024
// -----------------------------------------------------
package SementicError;

public class MissingDurationException extends Exception{

	private static final long serialVersionUID = 1L;
	public MissingDurationException(){
        super("SementicError: Missing Duration");
    }
    public MissingDurationException(String message){
        super(message);
    }
}
