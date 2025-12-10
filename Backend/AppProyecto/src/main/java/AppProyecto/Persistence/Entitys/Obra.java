package AppProyecto.persistence.entitys;

import java.time.LocalDateTime;

import AppProyecto.persistence.entitys.enumerados.Genero;
import AppProyecto.persistence.entitys.enumerados.Tipo;
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
	
	@Column(columnDefinition = "opinion")
	private int id_opinion;
	
	@Column(columnDefinition = "autor")
	private int id_autor;
	
	@OneToMany(mappedBy = "obra")
	private Autor autor;
	
	@ManyToOne
	@JoinColumn(name = "opinion", referencedColumnName = "id",
				insertable = false, updatable = false)
	private Opinion opinion;
}
