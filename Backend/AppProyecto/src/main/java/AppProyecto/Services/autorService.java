package AppProyecto.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import AppProyecto.Persistence.Entitys.autor;
import AppProyecto.Persistence.Repository.autorRepository;

@Service
public class autorService {

	@Autowired
	private autorRepository autorrepository;
	
	public List<autor> findAll(){
		return this.autorrepository.findAll();
	}
}
