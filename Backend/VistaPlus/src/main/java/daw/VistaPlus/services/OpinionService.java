package daw.VistaPlus.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import daw.VistaPlus.persistence.entities.Opinion;
import daw.VistaPlus.persistence.entities.Usuario;
import daw.VistaPlus.persistence.entities.Obra;
import daw.VistaPlus.persistence.repositories.OpinionRepository;
import daw.VistaPlus.persistence.repositories.UsuarioRepository;
import daw.VistaPlus.persistence.repositories.ObraRepository;
import daw.VistaPlus.services.dto.OpinionDTO;
import daw.VistaPlus.services.mappers.OpinionMapper;
import daw.VistaPlus.services.exceptions.OpinionNotFoundException;
import daw.VistaPlus.services.exceptions.UsuarioNotFoundException;
import daw.VistaPlus.services.exceptions.ObraNotFoundException;

@Service
public class OpinionService {

	@Autowired
	private OpinionRepository opinionRepository;

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private ObraRepository obraRepository;

	public List<OpinionDTO> findAll() {
		return this.opinionRepository.findAll().stream()
				.map(OpinionMapper::toDTO)
				.collect(Collectors.toList());
	}

	public OpinionDTO findById(int id) {
		Opinion opinion = this.opinionRepository.findById(id)
				.orElseThrow(() -> new OpinionNotFoundException("No se encuentra ninguna opinión con el id: " + id));
		return OpinionMapper.toDTO(opinion);
	}

	public OpinionDTO create(OpinionDTO dto) {
		Opinion opinion = OpinionMapper.toEntity(dto);

		if (dto.getUsuarioId() != 0) {
			Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
					.orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado"));
			opinion.setUsuario(usuario);
		}

		if (dto.getObraId() != 0) {
			Obra obra = obraRepository.findById(dto.getObraId())
					.orElseThrow(() -> new ObraNotFoundException("Obra no encontrada"));
			opinion.setObra(obra);
		}

		return OpinionMapper.toDTO(this.opinionRepository.save(opinion));
	}

	public OpinionDTO update(int id, OpinionDTO dto) {
		if (!this.opinionRepository.existsById(id)) {
			throw new OpinionNotFoundException("No se puede actualizar. Opinión no encontrada.");
		}

		Opinion opinion = OpinionMapper.toEntity(dto);
		opinion.setId(id);

		if (dto.getUsuarioId() != 0) {
			Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
					.orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado"));
			opinion.setUsuario(usuario);
		}

		if (dto.getObraId() != 0) {
			Obra obra = obraRepository.findById(dto.getObraId())
					.orElseThrow(() -> new ObraNotFoundException("Obra no encontrada"));
			opinion.setObra(obra);
		}

		return OpinionMapper.toDTO(this.opinionRepository.save(opinion));
	}

	public void deleteById(int id) {
		if (!this.opinionRepository.existsById(id)) {
			throw new OpinionNotFoundException("No se puede borrar. Opinión no encontrada.");
		}
		this.opinionRepository.deleteById(id);
	}
}
