package daw.VistaPlus.services.dto;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioDTO {
    private int id;
    private String nombre;
    private String email;
    private String password;
    private String nacionalidad;
    private LocalDateTime fechaNac;
    private String rol;
}
