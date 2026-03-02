package daw.VistaPlus.web.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import daw.VistaPlus.services.OpinionService;
import daw.VistaPlus.services.dto.OpinionDTO;
import daw.VistaPlus.services.exceptions.OpinionNotFoundException;

@RestController
@RequestMapping("/opinion")
public class OpinionController {

	@Autowired
	private OpinionService opinionService;

	@GetMapping
	public ResponseEntity<List<OpinionDTO>> findAll() {
		return ResponseEntity.ok(this.opinionService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable int id) {
		try {
			return ResponseEntity.ok(this.opinionService.findById(id));
		} catch (OpinionNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}

	@PostMapping
	public ResponseEntity<OpinionDTO> create(@RequestBody OpinionDTO dto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(this.opinionService.create(dto));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable int id, @RequestBody OpinionDTO dto) {
		try {
			return ResponseEntity.ok(this.opinionService.update(id, dto));
		} catch (OpinionNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable int id) {
		try {
			this.opinionService.deleteById(id);
			return ResponseEntity.noContent().build();
		} catch (OpinionNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}

	@PutMapping("/{id}/comentar")
	public ResponseEntity<OpinionDTO> comentar(@RequestBody OpinionDTO dto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(this.opinionService.saveOrUpdate(dto));
	}
}
