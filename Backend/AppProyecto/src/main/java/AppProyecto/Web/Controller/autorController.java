package AppProyecto.web.controller;

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

import AppProyecto.persistence.entitys.Autor;
import AppProyecto.services.AutorService;
import AppProyecto.services.dtos.AutorDTO;
import AppProyecto.services.exceptions.AutorNotFoundException;

@RestController
@RequestMapping("/autor")
public class AutorController {

	@Autowired
	private AutorService autorservice;
	
	@GetMapping
	public ResponseEntity<?> findAll(){
		return ResponseEntity.ok(this.autorservice.findAll());
	}
	
	@GetMapping
	public ResponseEntity<?> findById(@PathVariable int id){
		try {
			return ResponseEntity.ok(this.autorservice.findById(id));
		}catch (AutorNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}
	
	@PostMapping
    public ResponseEntity<?> create(@RequestBody AutorDTO dto) {
		try {
			return ResponseEntity.ok(this.autorservice.create(dto));
		}catch (AutorNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody Autor autor) {
    	try {
			return ResponseEntity.ok(this.autorservice.update(id, autor));
		}catch (AutorNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
    }
}
