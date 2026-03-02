package daw.VistaPlus.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import daw.VistaPlus.persistence.entities.Autor;
import daw.VistaPlus.persistence.repositories.AutorRepository;
import daw.VistaPlus.services.dto.AutorDTO;
import daw.VistaPlus.services.mappers.AutorMapper;
import daw.VistaPlus.persistence.repositories.ObraRepository;
import daw.VistaPlus.services.dto.ObraDTO;
import daw.VistaPlus.services.mappers.ObraMapper;
import daw.VistaPlus.services.exceptions.ObraNotFoundException;
import daw.VistaPlus.services.exceptions.AutorNotFoundException;

@Service
public class AutorService {
	@Autowired
	private AutorRepository autorRepository;

	@Autowired
	private ObraRepository obraRepository;

	public List<AutorDTO> findAll() {
		return this.autorRepository.findAll().stream()
				.map(AutorMapper::toDTO)
				.collect(Collectors.toList());
	}

	public AutorDTO findById(int id) {
		Autor autor = this.autorRepository.findById(id)
				.orElseThrow(() -> new AutorNotFoundException("No se encuentra ningún autor con el id: " + id));
		return AutorMapper.toDTO(autor);
	}

	public AutorDTO create(AutorDTO dto) {
		Autor autor = AutorMapper.toEntity(dto);
		return AutorMapper.toDTO(this.autorRepository.save(autor));
	}

	public AutorDTO update(int id, AutorDTO dto) {
		if (!this.autorRepository.existsById(id)) {
			throw new AutorNotFoundException("No se puede actualizar. Autor no encontrado.");
		}
		Autor autor = AutorMapper.toEntity(dto);
		autor.setId(id);
		return AutorMapper.toDTO(this.autorRepository.save(autor));
	}

	public void deleteById(int id) {
		if (!this.autorRepository.existsById(id)) {
			throw new AutorNotFoundException("No se puede borrar. Autor no encontrado.");
		}
		this.autorRepository.deleteById(id);
	}

	public ObraDTO addObra(int autorId, ObraDTO obraDto) {
		Autor autor = this.autorRepository.findById(autorId)
				.orElseThrow(() -> new AutorNotFoundException("Autor no encontrado"));

		Obra obra = ObraMapper.toEntity(obraDto);
		obra.setAutor(autor);
		return ObraMapper.toDTO(this.obraRepository.save(obra));
	}

	public ObraDTO updateObra(int autorId, int obraId, ObraDTO obraDto) {
		if (!this.autorRepository.existsById(autorId)) {
			throw new AutorNotFoundException("Autor no encontrado");
		}

		Obra obra = this.obraRepository.findById(obraId)
				.orElseThrow(() -> new ObraNotFoundException("Obra no encontrada"));

		if (obra.getAutor().getId() != autorId) {
			throw new IllegalArgumentException("Esta obra no pertenece a este autor");
		}

		Obra updatedObra = ObraMapper.toEntity(obraDto);
		updatedObra.setId(obraId);
		updatedObra.setAutor(obra.getAutor());

		return ObraMapper.toDTO(this.obraRepository.save(updatedObra));
	}
}
