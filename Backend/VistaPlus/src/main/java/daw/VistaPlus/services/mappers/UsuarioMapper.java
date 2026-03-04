package daw.VistaPlus.services.mappers;

import daw.VistaPlus.persistence.entities.Usuario;
import daw.VistaPlus.services.dto.UsuarioDTO;

public class UsuarioMapper {

    public static UsuarioDTO toDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setUsername(usuario.getUsername());
        dto.setEmail(usuario.getEmail());
        dto.setNacionalidad(usuario.getNacionalidad());
        dto.setFechaNac(usuario.getFechaNac());
        dto.setRol(usuario.getRol());
        // No enviamos el password por seguridad en el DTO de salida si fuera necesario,
        // pero para CRUD básico lo mantenemos si es un DTO de entrada/salida general.
        return dto;
    }

    public static Usuario toEntity(UsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setId(dto.getId());
        usuario.setUsername(dto.getUsername());
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(dto.getPassword());
        usuario.setNacionalidad(dto.getNacionalidad());
        usuario.setFechaNac(dto.getFechaNac());
        usuario.setRol(dto.getRol());
        return usuario;
    }
}
