package daw.VistaPlus.services.mappers;

import daw.VistaPlus.persistence.entities.Opinion;
import daw.VistaPlus.services.dto.OpinionDTO;

public class OpinionMapper {

    public static OpinionDTO toDTO(Opinion opinion) {
        OpinionDTO dto = new OpinionDTO();
        dto.setId(opinion.getId());
        dto.setComentario(opinion.getComentario());
        dto.setPuntuacion(opinion.getPuntuacion());
        dto.setMarcar(opinion.isMarcar());
        dto.setFecha(opinion.getFecha());
        if (opinion.getUsuario() != null) {
            dto.setUsuarioId(opinion.getUsuario().getId());
        }
        if (opinion.getObra() != null) {
            dto.setObraId(opinion.getObra().getId());
        }
        return dto;
    }

    public static Opinion toEntity(OpinionDTO dto) {
        Opinion opinion = new Opinion();
        opinion.setId(dto.getId());
        opinion.setComentario(dto.getComentario());
        opinion.setPuntuacion(dto.getPuntuacion());
        opinion.setMarcar(dto.isMarcar());
        opinion.setFecha(dto.getFecha());
        // Las relaciones se manejan en el servicio
        return opinion;
    }
}
