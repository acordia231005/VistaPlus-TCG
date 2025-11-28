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
}
