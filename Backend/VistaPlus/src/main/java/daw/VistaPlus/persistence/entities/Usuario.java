package daw.VistaPlus.persistence.entities;

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
@Table(name = "usuario")
public class Usuario {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 30, unique = true)
    private String username;

    @Column(length = 150)
    private String nacionalidad;

    @Column(length = 50)
    private String email;

    private String password;

    @Column(name = "fecha_nac")
    private LocalDateTime fechaNac;

    private String rol;
    
	@OneToMany(mappedBy = "usuario")
	@JsonIgnore
	private List<Opinion> opiniones;
	
	@OneToMany(mappedBy = "usuario")
	private List<Obra> obras;
}
