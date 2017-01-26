package pw.web.app.exception;

public class InvalidAppPropertyParametersException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidAppPropertyParametersException(){  
		
	} 
	
	public InvalidAppPropertyParametersException(String message){  
		super(message);
	} 
}
