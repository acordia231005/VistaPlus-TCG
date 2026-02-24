package daw.VistaPlus.services.mappers;

import daw.VistaPlus.persistence.entities.Autor;
import daw.VistaPlus.services.dto.AutorDTO;

public class AutorMapper {

    public static AutorDTO toDTO(Autor autor) {
        AutorDTO dto = new AutorDTO();
        dto.setId(autor.getId());
        dto.setNombre(autor.getNombre());
        dto.setNacionalidad(autor.getNacionalidad());
        dto.setEmail(autor.getEmail());
        dto.setFecha_nac(autor.getFechaNac());
        return dto;
    }

    public static Autor toEntity(AutorDTO dto) {
        Autor autor = new Autor();
        autor.setId(dto.getId());
        autor.setNombre(dto.getNombre());
        autor.setNacionalidad(dto.getNacionalidad());
        autor.setEmail(dto.getEmail());
        autor.setFechaNac(dto.getFecha_nac());
        return autor;
    }
}
