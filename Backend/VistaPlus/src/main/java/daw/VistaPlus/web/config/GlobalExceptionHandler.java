//package daw.VistaPlus.web.config;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//import daw.VistaPlus.services.exceptions.AutorException;
//import daw.VistaPlus.services.exceptions.AutorNotFoundException;
//import daw.VistaPlus.services.exceptions.ObraException;
//
//
//@RestControllerAdvice
//public class GlobalExceptionHandler {
//
//	@ExceptionHandler(AutorException.class)
//	public ResponseEntity<String> handleNotFound(AutorException ex){
//		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
//	}
//	
//	@ExceptionHandler(AutorNotFoundException.class)
//	public ResponseEntity<String> handleNotFound(AutorNotFoundException ex){
//		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
//	}
//	
//	@ExceptionHandler(ObraException.class)
//	public ResponseEntity<String> handleNotFound(ObraException ex){
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
//	}
//}