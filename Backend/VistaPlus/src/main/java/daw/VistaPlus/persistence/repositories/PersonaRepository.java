package daw.VistaPlus.persistence.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import daw.VistaPlus.persistence.entities.Persona;

public interface PersonaRepository<T extends Persona> extends JpaRepository<T, Integer>{
	Optional<T> findByUsername(String username);
	
}
