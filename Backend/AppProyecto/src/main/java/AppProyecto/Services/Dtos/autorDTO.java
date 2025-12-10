package AppProyecto.services.dtos;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AutorDTO {

	private String nombre;
    private String nacionalidad;
    private String email;
    private String password;
    private LocalDateTime fechaNac;
    private Integer obraId;
}
