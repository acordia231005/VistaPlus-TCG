package AppProyecto.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import AppProyecto.Persistence.Entitys.obra;
import AppProyecto.Persistence.Repository.obraRepository;
import AppProyecto.Services.Dtos.obraDTO;
import AppProyecto.Services.Mappers.obraMapper;

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
	
	// create
	public obraDTO create(obraDTO dto) {
        obra obra = new obra();

        obra.setTipo(dto.getTipo());
        obra.setTitulo(dto.getTitulo());
        obra.setGenero(dto.getGenero());
        obra.setSinopsis(dto.getSinopsis());
        obra.setYear(dto.getYear());
        obra.setId_opinion(dto.getIdOpinion());
        obra.setId_autor(dto.getIdAutor());

        return obraMapper.toDTO(obra);
    }
	// update
		
	// delete
	public void deleteById(int id) {
		if (this.obrarepository.existsById(id)) {
			this.obrarepository.deleteById(id);
		}
		return;
	}
}