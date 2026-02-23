package daw.VistaPlus.services.dto;
import java.time.LocalDateTime;
import daw.VistaPlus.persistence.entities.enums.Tipo;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ObraDTO {
	private int id;
    private Tipo tipo;
    private String titulo;
    private String sinopsis;
    private LocalDateTime year;
    private int idOpinion;
    private int idAutor;
}