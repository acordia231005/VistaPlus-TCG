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
        
        // Priorizar el ID de la columna directa si el objeto relación no está cargado
        dto.setUsuarioId(opinion.getIdUsuario());
        dto.setObraId(opinion.getIdObra());
        
        if (opinion.getUsuario() != null) {
            dto.setUsuarioId(opinion.getUsuario().getId());
            dto.setUsuarioUsername(opinion.getUsuario().getUsername());
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
        opinion.setIdUsuario(dto.getUsuarioId());
        opinion.setIdObra(dto.getObraId());
        return opinion;
    }
}
