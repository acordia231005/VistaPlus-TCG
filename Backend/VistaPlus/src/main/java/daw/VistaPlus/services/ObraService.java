package daw.VistaPlus.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import daw.VistaPlus.persistence.entities.Obra;
import daw.VistaPlus.persistence.entities.Usuario;
import daw.VistaPlus.persistence.repositories.ObraRepository;
import daw.VistaPlus.persistence.repositories.UsuarioRepository;
import daw.VistaPlus.services.dto.ObraDTO;
import daw.VistaPlus.services.mappers.ObraMapper;
import daw.VistaPlus.services.exceptions.ObraNotFoundException;
import daw.VistaPlus.services.exceptions.AutorNotFoundException;

@Service
public class ObraService {

	@Autowired
	private ObraRepository obraRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

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

		if (dto.getIdUsuario() != 0) {
			Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
					.orElseThrow(() -> new AutorNotFoundException("Autor no encontrado"));
			obra.setUsuario(usuario);
		}

		return ObraMapper.toDTO(this.obraRepository.save(obra));
	}

	public ObraDTO update(int id, ObraDTO dto) {
		if (!this.obraRepository.existsById(id)) {
			throw new ObraNotFoundException("No se puede actualizar. Obra no encontrada.");
		}

		Obra obra = ObraMapper.toEntity(dto);
		obra.setId(id);

		if (dto.getIdUsuario() != 0) {
			Usuario usuario = usuarioRepository.findById(dto.getIdUsuario())
					.orElseThrow(() -> new AutorNotFoundException("Autor no encontrado"));
			obra.setUsuario(usuario);
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
