package AppProyecto.Persistence.Entitys;

import java.time.LocalDateTime;
import java.util.List;

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
@Table(name = "opinion")
public class opinion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(columnDefinition = "varchar(30)")
	private String comentario;

	@Column(columnDefinition = "int")
	private int puntuacion;
	
	@Column(columnDefinition = "tinyint")
	private boolean marcar;
	
	@Column(columnDefinition = "Date")
	private LocalDateTime Fecha;
	
	@Column(name = "usuario")
	private int Usuario;
	
	@ManyToOne
	@JoinColumn(name = "usuario", referencedColumnName = "id",
				insertable = false, updatable = false)
	private usuario usuario;
	
	@OneToMany(mappedBy = "pedido")
	private List<obra> obras;
}
