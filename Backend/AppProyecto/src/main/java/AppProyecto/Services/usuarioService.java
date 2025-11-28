package AppProyecto.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import AppProyecto.Persistence.Entitys.usuario;
import AppProyecto.Persistence.Repository.usuarioRepository;

@Service
public class usuarioService {

	@Autowired
	private usuarioRepository usuariorepository;
	
	public List<usuario> findAll() {
		return this.usuariorepository.findAll();
	}
}
