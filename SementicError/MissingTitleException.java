// -----------------------------------------------------
// Assignment 2 - COMP 249
// Written by: Maëlys Arnaud 40278798
// Due date : March 27, 2024
// -----------------------------------------------------
package SementicError;

public class MissingTitleException extends Exception{

	private static final long serialVersionUID = 1L;
	public MissingTitleException(){
        super("SementicError: Missing Title");
    }
    public MissingTitleException(String message){
        super(message);
    }
}
