package daw.VistaPlus.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import daw.VistaPlus.services.OpinionService;
import daw.VistaPlus.services.exceptions.OpinionNotFoundException;


@RestController
@RequestMapping("/opinion")
public class OpinionController {

	@Autowired
	private OpinionService opinionservice;
	
	@GetMapping
	public ResponseEntity<?> findAll(){
		return ResponseEntity.ok(this.opinionservice.findAll());
	}
	
	@GetMapping
	public ResponseEntity<?> findById(@PathVariable int id){
		try {
			return ResponseEntity.ok(this.opinionservice.findById(id));
		}catch (OpinionNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
		}
	}
}
