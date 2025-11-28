package AppProyecto.Web.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import AppProyecto.Services.obraService;

@RestController
@RequestMapping("/obra")
public class obraController {

	@Autowired
	private obraService obraservice;
	
	@GetMapping
	public ResponseEntity<?> findAll(){
		return ResponseEntity.ok(this.obraservice.findAll());
	}
}
