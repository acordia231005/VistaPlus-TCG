package daw.VistaPlus.services.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginRequest {
	private String username;
	private String email;
	private String password;
	private String rol;
	private String nacionalidad;
	private String fechaNac;
}
