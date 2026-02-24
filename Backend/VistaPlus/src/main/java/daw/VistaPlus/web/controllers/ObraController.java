package daw.VistaPlus.web.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import daw.VistaPlus.services.ObraService;
import daw.VistaPlus.services.dto.ObraDTO;
import daw.VistaPlus.services.exceptions.ObraNotFoundException;

@RestController
@RequestMapping("/obra")
public class ObraController {

	@Autowired
	private ObraService obraService;

	@GetMapping
	public ResponseEntity<List<ObraDTO>> findAll() {
		return ResponseEntity.ok(this.obraService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable int id) {
		try {
			return ResponseEntity.ok(this.obraService.findById(id));
		} catch (ObraNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}

	@PostMapping
	public ResponseEntity<ObraDTO> create(@RequestBody ObraDTO dto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(this.obraService.create(dto));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable int id, @RequestBody ObraDTO dto) {
		try {
			return ResponseEntity.ok(this.obraService.update(id, dto));
		} catch (ObraNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable int id) {
		try {
			this.obraService.deleteById(id);
			return ResponseEntity.noContent().build();
		} catch (ObraNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}
}
