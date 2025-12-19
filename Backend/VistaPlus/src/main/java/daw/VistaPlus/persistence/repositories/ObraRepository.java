package daw.VistaPlus.persistence.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import daw.VistaPlus.persistence.entities.Obra;
public interface ObraRepository extends JpaRepository<Obra, Integer> {

}