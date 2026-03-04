package daw.VistaPlus.persistence.entities;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public abstract class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "varchar(30)", unique = true)
    private String username;

    @Column(columnDefinition = "varchar(150)")
    private String nacionalidad;

    @Column(columnDefinition = "varchar(50)")
    private String email;

    @Column(columnDefinition = "varchar(30)")
    private String password;

    @Column(columnDefinition = "Date")
    private LocalDateTime fechaNac;

    private String rol;
}
