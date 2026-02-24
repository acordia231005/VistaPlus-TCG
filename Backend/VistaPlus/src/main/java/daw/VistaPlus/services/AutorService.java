package daw.VistaPlus.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import daw.VistaPlus.persistence.entities.Autor;
import daw.VistaPlus.persistence.repositories.AutorRepository;
import daw.VistaPlus.services.dto.AutorDTO;
import daw.VistaPlus.services.mappers.AutorMapper;
import daw.VistaPlus.services.exceptions.AutorNotFoundException;

@Service
public class AutorService {
	@Autowired
	private AutorRepository autorRepository;

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
}
