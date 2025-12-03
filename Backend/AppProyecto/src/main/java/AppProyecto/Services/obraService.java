package AppProyecto.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import AppProyecto.Persistence.Entitys.autor;
import AppProyecto.Persistence.Entitys.obra;
import AppProyecto.Persistence.Repository.obraRepository;

@Service
public class obraService {

	@Autowired
	private obraRepository obrarepository;
	
	public List<obra> findAll(){
		return this.obrarepository.findAll();
	}
	
	public obra findById(int id) {
		if (this.obrarepository.existsById(id)) {
			throw new IllegalArgumentException("No se encuentra ninguna obra por ese id.");
		}
		return this.obrarepository.findById(id).get();
	}
}
