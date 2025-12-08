package AppProyecto.Web.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import AppProyecto.Persistence.Entitys.autor;
import AppProyecto.Services.autorService;
import AppProyecto.Services.Dtos.autorDTO;
import AppProyecto.Services.Exceptions.opinionNotFoundException;

@RestController
@RequestMapping("/autor")
public class autorController {

	@Autowired
	private autorService autorservice;
	
	@GetMapping
	public ResponseEntity<?> findAll(){
		return ResponseEntity.ok(this.autorservice.findAll());
	}
	
	@GetMapping
	public ResponseEntity<?> findById(@PathVariable int id){
		try {
			return ResponseEntity.ok(this.autorservice.findById(id));
		}catch (opinionNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}
	
	@PostMapping
    public ResponseEntity<?> create(@RequestBody autorDTO dto) {
		try {
			return ResponseEntity.ok(this.autorservice.create(dto));
		}catch (opinionNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody autor autor) {
    	try {
			return ResponseEntity.ok(this.autorservice.update(id, autor));
		}catch (opinionNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
    }
}
