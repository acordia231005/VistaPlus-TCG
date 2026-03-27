package daw.VistaPlus.persistence.entities;

import java.time.LocalDateTime;
import java.util.List;

import daw.VistaPlus.persistence.entities.enums.Tipo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

	@Enumerated(EnumType.STRING)
	private Tipo tipo;

	@Column(columnDefinition = "varchar(150)")
	private String titulo;

	@Column(columnDefinition = "text")
	private String sinopsis;

	private LocalDateTime year;
	
	@Column(name = "id_genero")
	private int idGenero;
	
	@Column(name = "id_usuario")
	private int idAutor;

	@ManyToOne
	@JoinColumn(name = "id_usuario", referencedColumnName = "id", insertable = false, updatable = false)
	private Usuario usuario ;

	@ManyToOne
	@JoinColumn(name = "id_genero", referencedColumnName = "id", insertable = false, updatable = false)
	private Genero genero;

	@OneToMany(mappedBy = "obra")
	private List<Opinion> opiniones;
}
