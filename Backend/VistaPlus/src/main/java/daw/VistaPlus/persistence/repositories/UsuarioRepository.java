package daw.VistaPlus.persistence.repositories;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import daw.VistaPlus.persistence.entities.Usuario;
public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
	List<Usuario> findByUsername(String username);
}