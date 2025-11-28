package AppProyecto.Persistence.Entitys;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "autor")
public class autor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(columnDefinition = "varchar(30)")
	private String nombre;
	
	@Column(columnDefinition = "varchar(150)")
	private String nacionalidad;

	@Column(columnDefinition = "varchar(50)")
	private String email;
	
	@Column(columnDefinition = "varchar(30)")
	private String password;
	
	@Column(columnDefinition = "Date")
	private LocalDateTime FechaNac;
	
	@OneToMany(mappedBy = "autor")
	@JsonIgnore
	private List<obra> obras;
}