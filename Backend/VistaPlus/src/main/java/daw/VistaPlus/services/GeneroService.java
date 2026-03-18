package daw.VistaPlus.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import daw.VistaPlus.persistence.entities.Genero;
import daw.VistaPlus.persistence.repositories.GeneroRepository;
import daw.VistaPlus.services.dto.GeneroDTO;
import daw.VistaPlus.services.mappers.GeneroMapper;

@Service
public class GeneroService {

    @Autowired
    private GeneroRepository generoRepository;

    public List<GeneroDTO> findAll() {
        return this.generoRepository.findAll().stream()
                .map(GeneroMapper::toDTO)
                .collect(Collectors.toList());
    }

    public GeneroDTO findById(int id) {
        Genero genero = this.generoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Género no encontrado"));
        return GeneroMapper.toDTO(genero);
    }

    public GeneroDTO create(GeneroDTO dto) {
        Genero genero = GeneroMapper.toEntity(dto);

        return GeneroMapper.toDTO(this.generoRepository.save(genero));
    }

    public GeneroDTO update(int id, GeneroDTO dto) {
        if (!this.generoRepository.existsById(id)) {
            throw new RuntimeException("Género no encontrado");
        }

        Genero genero = GeneroMapper.toEntity(dto);
        genero.setId(id);

        return GeneroMapper.toDTO(this.generoRepository.save(genero));
    }

    public void deleteById(int id) {
        if (!this.generoRepository.existsById(id)) {
            throw new RuntimeException("Género no encontrado");
        }
        this.generoRepository.deleteById(id);
    }
}
