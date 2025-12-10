package AppProyecto.services.mappers;

import AppProyecto.persistence.entitys.Obra;
import AppProyecto.services.dtos.ObraDTO;

public class ObraMapper {

	public static ObraDTO toDTO(Obra obra) {
		ObraDTO dto = new ObraDTO();

        dto.setId(obra.getId());
        dto.setTipo(obra.getTipo());
        dto.setTitulo(obra.getTitulo());
        dto.setGenero(obra.getGenero());
        dto.setSinopsis(obra.getSinopsis());
        dto.setYear(obra.getYear());
        dto.setIdOpinion(obra.getId_opinion());
        dto.setIdAutor(obra.getId_autor());

        return dto;
    }
}
