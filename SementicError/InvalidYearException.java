// -----------------------------------------------------
// Assignment 2 - COMP 249
// Written by: Maëlys Arnaud 40278798
// Due date : March 27, 2024
// -----------------------------------------------------
package SementicError;

public class InvalidYearException extends Exception{
	private static final long serialVersionUID = 1L;
	public InvalidYearException(){
        super("SementicError: Invalid Year");
    }
    public InvalidYearException(String message){
        super(message);
    }

}
