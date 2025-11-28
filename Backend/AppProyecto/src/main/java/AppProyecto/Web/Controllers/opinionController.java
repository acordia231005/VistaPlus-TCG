package AppProyecto.Web.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import AppProyecto.Services.opinionService;

@RestController
@RequestMapping("/opinion")
public class opinionController {

	@Autowired
	private opinionService opinionservice;
	
	@GetMapping
	public ResponseEntity<?> findAll(){
		return ResponseEntity.ok(this.opinionservice.findAll());
	}
}
