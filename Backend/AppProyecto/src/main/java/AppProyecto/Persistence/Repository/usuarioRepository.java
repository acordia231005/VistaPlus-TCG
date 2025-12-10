package AppProyecto.persistence.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import AppProyecto.persistence.entitys.Usuario;
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{

}