package daw.VistaPlus.persistence.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import daw.VistaPlus.persistence.entities.Opinion;

public interface OpinionRepository extends JpaRepository<Opinion, Integer> {
    Optional<Opinion> findByUsuarioIdAndObraId(int usuarioId, int obraId);
}