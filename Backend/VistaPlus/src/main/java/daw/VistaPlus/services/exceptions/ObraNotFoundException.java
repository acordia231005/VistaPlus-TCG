package daw.VistaPlus.services.exceptions;
public class ObraNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	public ObraNotFoundException(String message) {
		super(message);
	}
}