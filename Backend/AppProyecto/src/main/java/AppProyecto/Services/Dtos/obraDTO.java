package AppProyecto.services.dtos;

import java.time.LocalDateTime;
import AppProyecto.persistence.entitys.enumerados.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ObraDTO {

	private int id;
    private Tipo tipo;
    private String titulo;
    private Genero genero;
    private String sinopsis;
    private LocalDateTime year;
    private int idOpinion;
    private int idAutor;
    
}