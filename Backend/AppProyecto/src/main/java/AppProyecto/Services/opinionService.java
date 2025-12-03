package AppProyecto.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import AppProyecto.Persistence.Entitys.opinion;
import AppProyecto.Persistence.Repository.opinionRepository;

@Service
public class opinionService {

	@Autowired
	private opinionRepository opinionrepository;
	
	public List<opinion> findAll(){
		return this.opinionrepository.findAll();
	}
	
	public opinion findById(int id) {
		if (this.opinionrepository.existsById(id)) {
			throw new IllegalArgumentException("No se encuentra ninguna opinion por ese id.");
		}
		return this.opinionrepository.findById(id).get();
	}
}
