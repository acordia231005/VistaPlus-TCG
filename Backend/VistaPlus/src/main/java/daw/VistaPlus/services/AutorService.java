package daw.VistaPlus.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import daw.VistaPlus.persistence.entities.Autor;
import daw.VistaPlus.persistence.repositories.AutorRepository;
import daw.VistaPlus.services.dto.AutorDTO;


@Service
public class AutorService {
	@Autowired
	private AutorRepository autorrepository;
	
	public List<Autor> findAll(){
		return this.autorrepository.findAll();
	}
	
	public Autor findById(int id) {
		if (this.autorrepository.existsById(id)) {
			throw new IllegalArgumentException("No se encuentra ningun autor por ese id.");
		}
		return this.autorrepository.findById(id).get();
	}
	
	// create
	public Autor create(AutorDTO dto) {

        Autor nuevoAutor = new Autor();

        nuevoAutor.setNombre(dto.getNombre());
        nuevoAutor.setNacionalidad(dto.getNacionalidad());
        nuevoAutor.setEmail(dto.getEmail());
        nuevoAutor.setFechaNac(dto.getFecha_nac());

        return this.autorrepository.save(nuevoAutor);
    }
	
	// update
	public Autor update(int id, Autor cambios) {
        Autor autorExistente = this.findById(id);

        autorExistente.setNombre(cambios.getNombre());
        autorExistente.setEmail(cambios.getEmail());
        autorExistente.setNacionalidad(cambios.getNacionalidad());
        autorExistente.setFechaNac(cambios.getFechaNac());
        autorExistente.setObra(cambios.getObra());

        return this.autorrepository.save(autorExistente);
    }
	
	// delete
	public void deleteById(int id) {
		if (this.autorrepository.existsById(id)) {
			this.autorrepository.deleteById(id);
		}
		return;
	}
}
