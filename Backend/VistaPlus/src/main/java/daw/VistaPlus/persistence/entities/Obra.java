package daw.VistaPlus.persistence.entities;

import java.time.LocalDateTime;
import java.util.List;

import daw.VistaPlus.persistence.entities.enums.Tipo;
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

	@Column(columnDefinition = "text")
	private String sinopsis;

	@Column(columnDefinition = "Date")
	private LocalDateTime year;

	@ManyToOne
	@JoinColumn(name = "autor_id")
	private Autor autor;

	@OneToMany(mappedBy = "obra")
	private List<Genero> generos;

	@OneToMany(mappedBy = "obra")
	private List<Opinion> opiniones;
}
