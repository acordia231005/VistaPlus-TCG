package daw.VistaPlus.persistence.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "opinion")
public class Opinion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "id_usuario")
	private int idUsuario;
	
	@Column(name = "id_obra")
	private int idObra;
	
	@Column(columnDefinition = "varchar(500)")
	private String comentario;

	@Column(columnDefinition = "int")
	private int puntuacion;

	@Column(columnDefinition = "tinyint")
	private boolean marcar;

	@Column(columnDefinition = "Date")
	private LocalDateTime fecha;

	@ManyToOne
	@JoinColumn(name = "usuario_id", referencedColumnName = "id",
			insertable = false, updatable = false)
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(name = "obra_id", referencedColumnName = "id",
			insertable = false, updatable = false)
	private Obra obra;
}
