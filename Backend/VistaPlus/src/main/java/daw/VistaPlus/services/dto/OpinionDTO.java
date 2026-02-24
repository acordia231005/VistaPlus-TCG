package daw.VistaPlus.services.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OpinionDTO {
    private int id;
    private String comentario;
    private int puntuacion;
    private boolean marcar;
    private LocalDateTime fecha;
    private int usuarioId;
    private int obraId;
}
