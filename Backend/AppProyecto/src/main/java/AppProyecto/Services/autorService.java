package AppProyecto.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import AppProyecto.Persistence.Entitys.autor;
import AppProyecto.Persistence.Entitys.obra;
import AppProyecto.Persistence.Repository.autorRepository;
import AppProyecto.Services.Dtos.autorDTO;

@Service
public class autorService {

	@Autowired
	private autorRepository autorrepository;
	
	public List<autor> findAll(){
		return this.autorrepository.findAll();
	}
	
	public autor findById(int id) {
		if (this.autorrepository.existsById(id)) {
			throw new IllegalArgumentException("No se encuentra ningun autor por ese id.");
		}
		return this.autorrepository.findById(id).get();
	}
	
	// create
	public autor create(autorDTO dto) {

        autor nuevoAutor = new autor();

        nuevoAutor.setNombre(dto.getNombre());
        nuevoAutor.setNacionalidad(dto.getNacionalidad());
        nuevoAutor.setEmail(dto.getEmail());
        nuevoAutor.setPassword(dto.getPassword());
        nuevoAutor.setFechaNac(dto.getFechaNac());

        return autorRepository.save(nuevoAutor);
    }
	
	// update
	public autor update(int id, autor updatedData) {
        autor autorExistente = this.findById(id);

        autorExistente.setNombre(updatedData.getNombre());
        autorExistente.setEmail(updatedData.getEmail());
        autorExistente.setNacionalidad(updatedData.getNacionalidad());
        autorExistente.setPassword(updatedData.getPassword());
        autorExistente.setFechaNac(updatedData.getFechaNac());
        autorExistente.setObra(updatedData.getObra());

        return autorRepository.save(autorExistente);
    }
	
	// delete
	public void deleteById(int id) {
		if (this.autorrepository.existsById(id)) {
			this.autorrepository.deleteById(id);
		}
		return;
	}
}