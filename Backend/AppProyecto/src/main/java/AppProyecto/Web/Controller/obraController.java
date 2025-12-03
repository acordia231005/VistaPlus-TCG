package AppProyecto.Web.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import AppProyecto.Services.obraService;
import AppProyecto.Services.Exceptions.opinionNotFoundException;

@RestController
@RequestMapping("/obra")
public class obraController {

	@Autowired
	private obraService obraservice;
	
	@GetMapping
	public ResponseEntity<?> findAll(){
		return ResponseEntity.ok(this.obraservice.findAll());
	}
	
	@GetMapping
	public ResponseEntity<?> findById(@PathVariable int id){
		try {
			return ResponseEntity.ok(this.obraservice.findById(id));
		}catch (opinionNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}
}
