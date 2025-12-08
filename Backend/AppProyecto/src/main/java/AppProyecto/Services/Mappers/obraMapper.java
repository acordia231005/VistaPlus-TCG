package AppProyecto.Services.Mappers;

import AppProyecto.Persistence.Entitys.obra;
import AppProyecto.Services.Dtos.obraDTO;

public class obraMapper {

	public static obraDTO toDTO(obra obra) {
        obraDTO dto = new obraDTO();

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
