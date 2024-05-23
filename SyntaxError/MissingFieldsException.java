// -----------------------------------------------------
// Assignment 2 - COMP 249
// Written by: Maëlys Arnaud 40278798
// Due date : March 27, 2024
// -----------------------------------------------------
package SyntaxError;

public class MissingFieldsException extends Exception{

	private static final long serialVersionUID = 1L;
	public MissingFieldsException(){
        super("SementicError: Missing field(s)");
    }
    public MissingFieldsException(String message){
        super(message);
    }
}
