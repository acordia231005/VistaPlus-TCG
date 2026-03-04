package daw.VistaPlus.services.mappers;

import daw.VistaPlus.persistence.entities.Autor;
import daw.VistaPlus.services.dto.AutorDTO;

public class AutorMapper {

    public static AutorDTO toDTO(Autor autor) {
        AutorDTO dto = new AutorDTO();
        dto.setId(autor.getId());
        dto.setUsername(autor.getUsername());
        dto.setNacionalidad(autor.getNacionalidad());
        dto.setEmail(autor.getEmail());
        dto.setFechaNac(autor.getFechaNac());
        return dto;
    }

    public static Autor toEntity(AutorDTO dto) {
        Autor autor = new Autor();
        autor.setId(dto.getId());
        autor.setUsername(dto.getUsername());
        autor.setNacionalidad(dto.getNacionalidad());
        autor.setEmail(dto.getEmail());
        autor.setFechaNac(dto.getFechaNac());
        return autor;
    }
}
