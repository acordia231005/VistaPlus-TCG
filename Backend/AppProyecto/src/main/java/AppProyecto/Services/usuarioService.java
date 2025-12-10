package AppProyecto.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import AppProyecto.persistence.entitys.Usuario;
import AppProyecto.persistence.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuariorepository;
	
	public List<Usuario> findAll() {
		return this.usuariorepository.findAll();
	}
	
	public Usuario findById(int id) {
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