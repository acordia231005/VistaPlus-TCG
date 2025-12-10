package AppProyecto.persistence.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import AppProyecto.persistence.entitys.Autor;
public interface AutorRepository extends JpaRepository<Autor, Integer>{

}