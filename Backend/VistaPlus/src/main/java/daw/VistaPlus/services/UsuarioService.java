package daw.VistaPlus.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import daw.VistaPlus.persistence.entities.Usuario;
import daw.VistaPlus.persistence.repositories.UsuarioRepository;
import daw.VistaPlus.services.dto.UsuarioDTO;
import daw.VistaPlus.services.mappers.UsuarioMapper;
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

        usuarioExistente.setNombre(dto.getNombre());
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
}
