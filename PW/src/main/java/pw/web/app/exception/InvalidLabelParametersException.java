package pw.web.app.exception;

public class InvalidLabelParametersException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public InvalidLabelParametersException(){  
		
	} 
	
	public InvalidLabelParametersException(String message){  
		super(message);
	}
}
