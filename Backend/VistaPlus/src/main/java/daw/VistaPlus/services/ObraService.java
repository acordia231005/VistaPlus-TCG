package daw.VistaPlus.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import daw.VistaPlus.persistence.entities.Obra;
import daw.VistaPlus.persistence.entities.Autor;
import daw.VistaPlus.persistence.repositories.ObraRepository;
import daw.VistaPlus.persistence.repositories.AutorRepository;
import daw.VistaPlus.services.dto.ObraDTO;
import daw.VistaPlus.services.mappers.ObraMapper;
import daw.VistaPlus.services.exceptions.ObraNotFoundException;
import daw.VistaPlus.services.exceptions.AutorNotFoundException;

@Service
public class ObraService {

	@Autowired
	private ObraRepository obraRepository;

	@Autowired
	private AutorRepository autorRepository;

	public List<ObraDTO> findAll() {
		return this.obraRepository.findAll().stream()
				.map(ObraMapper::toDTO)
				.collect(Collectors.toList());
	}

	public ObraDTO findById(int id) {
		Obra obra = this.obraRepository.findById(id)
				.orElseThrow(() -> new ObraNotFoundException("No se encuentra ninguna obra con el id: " + id));
		return ObraMapper.toDTO(obra);
	}

	public ObraDTO create(ObraDTO dto) {
		Obra obra = ObraMapper.toEntity(dto);

		if (dto.getIdAutor() != 0) {
			Autor autor = autorRepository.findById(dto.getIdAutor())
					.orElseThrow(() -> new AutorNotFoundException("Autor no encontrado"));
			obra.setAutor(autor);
		}

		return ObraMapper.toDTO(this.obraRepository.save(obra));
	}

	public ObraDTO update(int id, ObraDTO dto) {
		if (!this.obraRepository.existsById(id)) {
			throw new ObraNotFoundException("No se puede actualizar. Obra no encontrada.");
		}

		Obra obra = ObraMapper.toEntity(dto);
		obra.setId(id);

		if (dto.getIdAutor() != 0) {
			Autor autor = autorRepository.findById(dto.getIdAutor())
					.orElseThrow(() -> new AutorNotFoundException("Autor no encontrado"));
			obra.setAutor(autor);
		}

		return ObraMapper.toDTO(this.obraRepository.save(obra));
	}

	public void deleteById(int id) {
		if (!this.obraRepository.existsById(id)) {
			throw new ObraNotFoundException("No se puede borrar. Obra no encontrada.");
		}
		this.obraRepository.deleteById(id);
	}
}
