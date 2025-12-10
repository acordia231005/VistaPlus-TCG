package AppProyecto.persistence.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import AppProyecto.persistence.entitys.Obra;
public interface ObraRepository extends JpaRepository<Obra, Integer>{
	
}