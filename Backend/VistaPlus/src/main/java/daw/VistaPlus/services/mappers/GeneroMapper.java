package daw.VistaPlus.services.mappers;

import daw.VistaPlus.persistence.entities.Genero;
import daw.VistaPlus.services.dto.GeneroDTO;

public class GeneroMapper {
    public static GeneroDTO toDTO(Genero genero) {
        GeneroDTO dto = new GeneroDTO();
        dto.setId(genero.getId());
        dto.setNombre(genero.getNombre());
        return dto;
    }

    public static Genero toEntity(GeneroDTO dto) {
        Genero genero = new Genero();
        genero.setId(dto.getId());
        genero.setNombre(dto.getNombre());
        return genero;
    }
}