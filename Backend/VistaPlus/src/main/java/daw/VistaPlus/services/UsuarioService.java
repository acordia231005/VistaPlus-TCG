package daw.VistaPlus.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import daw.VistaPlus.persistence.entities.Obra;
import daw.VistaPlus.persistence.entities.Usuario;
import daw.VistaPlus.persistence.repositories.UsuarioRepository;
import daw.VistaPlus.services.dto.ObraDTO;
import daw.VistaPlus.services.dto.UsuarioDTO;
import daw.VistaPlus.services.mappers.ObraMapper;
import daw.VistaPlus.services.mappers.UsuarioMapper;
import daw.VistaPlus.services.exceptions.AutorNotFoundException;
import daw.VistaPlus.services.exceptions.ObraNotFoundException;
import daw.VistaPlus.services.exceptions.UsuarioNotFoundException;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<UsuarioDTO> findAll() {
        return this.usuarioRepository.findAll().stream()
                .map(UsuarioMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UsuarioDTO findById(int id) {
        Usuario usuario = this.usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado con id: " + id));
        return UsuarioMapper.toDTO(usuario);
    }

    public UsuarioDTO create(UsuarioDTO dto) {
        Usuario usuario = UsuarioMapper.toEntity(dto);
        usuario.setPassword(dto.getPassword());
        if (usuario.getRol() == null) {
            usuario.setRol("USER");
        }
        return UsuarioMapper.toDTO(this.usuarioRepository.save(usuario));
    }

    public UsuarioDTO update(int id, UsuarioDTO dto) {
        Usuario usuarioExistente = this.usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado"));

        usuarioExistente.setUsername(dto.getUsername());
        usuarioExistente.setEmail(dto.getEmail());
        usuarioExistente.setNacionalidad(dto.getNacionalidad());
        usuarioExistente.setFechaNac(dto.getFechaNac());
        usuarioExistente.setRol(dto.getRol());

        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            usuarioExistente.setPassword(dto.getPassword());
        }

        return UsuarioMapper.toDTO(this.usuarioRepository.save(usuarioExistente));
    }

    public void deleteById(int id) {
        if (!this.usuarioRepository.existsById(id)) {
            throw new UsuarioNotFoundException("Usuario no encontrado");
        }
        this.usuarioRepository.deleteById(id);
    }

    public Usuario findByUsername(String username) {
        return this.usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsuarioNotFoundException("El usuario " + username + " no existe. "));
    }
    
    public ObraDTO addObra(int autorId, ObraDTO obraDto) {
		Usuario usuario = this.usuarioRepository.findById(autorId)
				.orElseThrow(() -> new AutorNotFoundException("Autor no encontrado"));

		Obra obra = ObraMapper.toEntity(obraDto);
		obra.setUsuario(usuario);
		return ObraMapper.toDTO(this.usuarioRepository.save(obra));
	}

	public ObraDTO updateObra(int autorId, int obraId, ObraDTO obraDto) {
		if (!this.usuarioRepository.existsById(autorId)) {
			throw new AutorNotFoundException("Autor no encontrado");
		}

		Obra obra = this.usuarioRepository.findById(obraId)
				.orElseThrow(() -> new ObraNotFoundException("Obra no encontrada"));

		if (obra.getUsuario().getId() != autorId) {
			throw new IllegalArgumentException("Esta obra no pertenece a este autor");
		}

		Obra updatedObra = ObraMapper.toEntity(obraDto);
		updatedObra.setId(obraId);
		updatedObra.setUsuario(obra.getUsuario());

		return ObraMapper.toDTO(this.usuarioRepository.save(updatedObra));
	}
}
