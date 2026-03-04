package daw.VistaPlus.services.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AutorDTO {
	private int id;
	private String username;
	private String nacionalidad;
	private String email;
	private LocalDateTime fechaNac;
}