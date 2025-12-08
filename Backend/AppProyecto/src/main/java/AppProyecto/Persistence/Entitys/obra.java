package AppProyecto.Persistence.Entitys;

import java.time.LocalDateTime;

import AppProyecto.Persistence.Entitys.Enum.genero;
import AppProyecto.Persistence.Entitys.Enum.tipo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "obra")
public class obra {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(columnDefinition = "enum")
	private tipo tipo;
	
	@Column(columnDefinition = "varchar(150)")
	private String titulo;

	@Column(columnDefinition = "enum")
	private genero genero;
	
	@Column(columnDefinition = "text")
	private String sinopsis;
	
	@Column(columnDefinition = "Date")
	private LocalDateTime year;
	
	@Column(columnDefinition = "opinion")
	private int id_opinion;
	
	@Column(columnDefinition = "autor")
	private int id_autor;
	
	@OneToMany(mappedBy = "obra")
	private autor autor;
	
	@ManyToOne
	@JoinColumn(name = "opinion", referencedColumnName = "id",
				insertable = false, updatable = false)
	private opinion opinion;
}
