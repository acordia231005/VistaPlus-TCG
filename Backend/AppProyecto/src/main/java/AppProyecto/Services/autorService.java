package AppProyecto.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import AppProyecto.persistence.entitys.Autor;
import AppProyecto.persistence.repository.AutorRepository;
import AppProyecto.services.dtos.AutorDTO;



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
        nuevoAutor.setPassword(dto.getPassword());
        nuevoAutor.setFechaNac(dto.getFechaNac());

        return this.autorrepository.save(nuevoAutor);
    }
	
	// update
	public Autor update(int id, Autor updatedData) {
        Autor autorExistente = this.findById(id);

        autorExistente.setNombre(updatedData.getNombre());
        autorExistente.setEmail(updatedData.getEmail());
        autorExistente.setNacionalidad(updatedData.getNacionalidad());
        autorExistente.setPassword(updatedData.getPassword());
        autorExistente.setFechaNac(updatedData.getFechaNac());
        autorExistente.setObra(updatedData.getObra());

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