package AppProyecto.Services.Dtos;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class autorDTO {

	private String nombre;
    private String nacionalidad;
    private String email;
    private String password;
    private LocalDateTime fechaNac;
    private Integer obraId;
}
