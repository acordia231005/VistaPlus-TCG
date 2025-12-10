package AppProyecto.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import AppProyecto.persistence.entitys.Obra;
import AppProyecto.persistence.repository.ObraRepository;
import AppProyecto.services.dtos.ObraDTO;
import AppProyecto.services.mappers.ObraMapper;


@Service
public class ObraService {

	@Autowired
	private ObraRepository obrarepository;
	
	public List<Obra> findAll(){
		return this.obrarepository.findAll();
	}
	
	public Obra findById(int id) {
		if (this.obrarepository.existsById(id)) {
			throw new IllegalArgumentException("No se encuentra ninguna obra por ese id.");
		}
		return this.obrarepository.findById(id).get();
	}
	
	// create
	public ObraDTO create(ObraDTO dto) {
        Obra obra = new Obra();

        obra.setTipo(dto.getTipo());
        obra.setTitulo(dto.getTitulo());
        obra.setGenero(dto.getGenero());
        obra.setSinopsis(dto.getSinopsis());
        obra.setYear(dto.getYear());
        obra.setId_opinion(dto.getIdOpinion());
        obra.setId_autor(dto.getIdAutor());

        return ObraMapper.toDTO(obra);
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