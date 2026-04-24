package daw.VistaPlus.persistence.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import daw.VistaPlus.persistence.entities.Opinion;
import daw.VistaPlus.persistence.entities.Usuario;

public interface OpinionRepository extends JpaRepository<Opinion, Integer> {
    Optional<Opinion> findByUsuarioIdAndObraId(int usuarioId, int obraId);
    Optional<Opinion> findByIdUsuarioAndIdObra(int idUsuario, int idObra);
}