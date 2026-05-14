package daw.VistaPlus.services.mappers;

import daw.VistaPlus.persistence.entities.Obra;
import daw.VistaPlus.services.dto.ObraDTO;

public class ObraMapper {

    public static ObraDTO toDTO(Obra obra) {
        ObraDTO dto = new ObraDTO();
        dto.setId(obra.getId());
        dto.setTipo(obra.getTipo());
        dto.setTitulo(obra.getTitulo());
        dto.setSinopsis(obra.getSinopsis());
        dto.setYear(obra.getYear());
        dto.setImagen(obra.getImagen());
        if (obra.getUsuario() != null) {
            dto.setIdUsuario(obra.getUsuario().getId());
            dto.setAutorUsername(obra.getUsuario().getUsername());
        }
        if (obra.getGenero() != null) {
            dto.setIdGenero(obra.getGenero().getId());
            dto.setGeneroNombre(obra.getGenero().getNombre());
        }
        return dto;
    }

    public static Obra toEntity(ObraDTO dto) {
        Obra obra = new Obra();
        obra.setId(dto.getId());
        obra.setTipo(dto.getTipo());
        obra.setTitulo(dto.getTitulo());
        obra.setSinopsis(dto.getSinopsis());
        obra.setYear(dto.getYear());
        obra.setImagen(dto.getImagen());
        obra.setIdGenero(dto.getIdGenero());
        obra.setIdUsuario(dto.getIdUsuario());
        return obra;
    }
}
