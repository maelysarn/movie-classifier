// -----------------------------------------------------
// Assignment 2 - COMP 249
// Written by: Maëlys Arnaud 40278798
// Due date : March 27, 2024
// -----------------------------------------------------
package SementicError;

public class MissingNamesException extends Exception{

	private static final long serialVersionUID = 1L;
	public MissingNamesException(){
        super("SementicError: Missing name(s)");
    }
    public MissingNamesException(String message){
        super(message);
    }
}
