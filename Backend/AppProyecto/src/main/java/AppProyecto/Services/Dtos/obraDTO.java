package AppProyecto.Services.Dtos;

import java.time.LocalDateTime;
import AppProyecto.Persistence.Entitys.Enum.*;

public class obraDTO {

	private int id;
    private tipo tipo;
    private String titulo;
    private genero genero;
    private String sinopsis;
    private LocalDateTime year;
    private int idOpinion;
    private int idAutor;
    
}