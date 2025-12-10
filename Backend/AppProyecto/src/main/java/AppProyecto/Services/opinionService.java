package AppProyecto.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import AppProyecto.persistence.entitys.Opinion;
import AppProyecto.persistence.repository.OpinionRepository;


@Service
public class OpinionService {

	@Autowired
	private OpinionRepository opinionrepository;
	
	public List<Opinion> findAll(){
		return this.opinionrepository.findAll();
	}
	
	public Opinion findById(int id) {
		if (this.opinionrepository.existsById(id)) {
			throw new IllegalArgumentException("No se encuentra ninguna opinion por ese id.");
		}
		return this.opinionrepository.findById(id).get();
	}
	
	// create
	
	// update
		
	// delete
	public void deleteById(int id) {
		if (this.opinionrepository.existsById(id)) {
			this.opinionrepository.deleteById(id);
		}
		return;
	}
}