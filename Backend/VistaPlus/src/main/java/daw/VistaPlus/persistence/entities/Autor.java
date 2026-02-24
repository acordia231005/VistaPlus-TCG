package daw.VistaPlus.persistence.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "autor")
public class Autor extends Persona {

	@OneToMany(mappedBy = "autor")
	private List<Obra> obras;
}
