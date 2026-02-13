package daw.VistaPlus.persistence.repositories;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import daw.VistaPlus.persistence.entities.Usuario;
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
	Optional<Usuario> findByUsername(String username);
}