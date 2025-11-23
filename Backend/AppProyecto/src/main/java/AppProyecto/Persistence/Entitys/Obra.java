package AppProyecto.Persistence.Entitys;

import java.time.LocalDateTime;

import AppProyecto.Persistence.Entitys.Enum.Genero;
import AppProyecto.Persistence.Entitys.Enum.Tipo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "obra")
public class Obra {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(columnDefinition = "enum")
	private Tipo tipo;
	
	@Column(columnDefinition = "varchar(150)")
	private String titulo;

	@Column(columnDefinition = "enum")
	private Genero genero;
	
	@Column(columnDefinition = "text")
	private String sinopsis;
	
	@Column(columnDefinition = "Date")
	private LocalDateTime year;
}
