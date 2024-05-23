// -----------------------------------------------------
// Assignment 2 - COMP 249
// Written by: Maëlys Arnaud 40278798
// Due date : March 27, 2024
// -----------------------------------------------------
package SyntaxError;

public class ExcessFieldsException extends Exception{

	private static final long serialVersionUID = 1L;
	public ExcessFieldsException(){
        super("SyntaxError: Excess Fields");
    }
    public ExcessFieldsException(String message){
        super(message);
    }
}
