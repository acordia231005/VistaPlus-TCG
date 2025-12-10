package AppProyecto.persistence.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import AppProyecto.persistence.entitys.Opinion;
public interface OpinionRepository extends JpaRepository<Opinion, Integer> {

}