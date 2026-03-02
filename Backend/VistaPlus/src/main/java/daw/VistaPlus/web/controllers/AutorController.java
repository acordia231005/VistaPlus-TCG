package daw.VistaPlus.web.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import daw.VistaPlus.services.AutorService;
import daw.VistaPlus.services.dto.AutorDTO;
import daw.VistaPlus.services.dto.ObraDTO;
import daw.VistaPlus.services.exceptions.ObraNotFoundException;
import daw.VistaPlus.services.exceptions.AutorNotFoundException;

@RestController
@RequestMapping("/autor")
public class AutorController {

	@Autowired
	private AutorService autorService;

	@GetMapping
	public ResponseEntity<List<AutorDTO>> findAll() {
		return ResponseEntity.ok(this.autorService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable int id) {
		try {
			return ResponseEntity.ok(this.autorService.findById(id));
		} catch (AutorNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}

	@PostMapping
	public ResponseEntity<AutorDTO> create(@RequestBody AutorDTO dto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(this.autorService.create(dto));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable int id, @RequestBody AutorDTO dto) {
		try {
			return ResponseEntity.ok(this.autorService.update(id, dto));
		} catch (AutorNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable int id) {
		try {
			this.autorService.deleteById(id);
			return ResponseEntity.noContent().build();
		} catch (AutorNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}

	@PostMapping("/{id}/obra")
	public ResponseEntity<?> addObra(@PathVariable int id, @RequestBody ObraDTO obraDto) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED).body(this.autorService.addObra(id, obraDto));
		} catch (AutorNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}

	@PutMapping("/{id}/obra/{obraId}")
	public ResponseEntity<?> updateObra(@PathVariable int id, @PathVariable int obraId, @RequestBody ObraDTO obraDto) {
		try {
			return ResponseEntity.ok(this.autorService.updateObra(id, obraId, obraDto));
		} catch (AutorNotFoundException | ObraNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		} catch (IllegalArgumentException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
		}
	}
}
