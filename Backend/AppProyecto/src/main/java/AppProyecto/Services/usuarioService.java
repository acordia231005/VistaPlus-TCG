package AppProyecto.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import AppProyecto.Persistence.Entitys.obra;
import AppProyecto.Persistence.Entitys.usuario;
import AppProyecto.Persistence.Repository.usuarioRepository;

@Service
public class usuarioService {

	@Autowired
	private usuarioRepository usuariorepository;
	
	public List<usuario> findAll() {
		return this.usuariorepository.findAll();
	}
	
	public usuario findById(int id) {
		if (this.usuariorepository.existsById(id)) {
			throw new IllegalArgumentException("No se encuentra ningun usuario por ese id.");
		}
		return this.usuariorepository.findById(id).get();
	}
	
	// create
	
	// update
		
	// delete
	public void deleteById(int id) {
		if (this.usuariorepository.existsById(id)) {
			this.usuariorepository.deleteById(id);
		}
		return;
	}
}