package daw.VistaPlus.services.exceptions;
public class OpinionNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	public OpinionNotFoundException(String message) {
		super(message);
	}
}