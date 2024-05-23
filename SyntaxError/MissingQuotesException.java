// -----------------------------------------------------
// Assignment 2 - COMP 249
// Written by: Maëlys Arnaud 40278798
// Due date : March 27, 2024
// -----------------------------------------------------
package SyntaxError;

public class MissingQuotesException extends Exception{

	private static final long serialVersionUID = 1L;
	public MissingQuotesException(){
        super("SementicError: Missing Quotes");
    }
    public MissingQuotesException(String message){
        super(message);
    }
}