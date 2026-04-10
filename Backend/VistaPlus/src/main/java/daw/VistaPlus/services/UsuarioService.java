package daw.VistaPlus.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import daw.VistaPlus.persistence.entities.Obra;
import daw.VistaPlus.persistence.entities.Opinion;
import daw.VistaPlus.persistence.entities.Usuario;
import daw.VistaPlus.persistence.repositories.ObraRepository;
import daw.VistaPlus.persistence.repositories.OpinionRepository;
import daw.VistaPlus.persistence.repositories.UsuarioRepository;
import daw.VistaPlus.services.dto.ObraDTO;
import daw.VistaPlus.services.dto.OpinionDTO;
import daw.VistaPlus.services.dto.UsuarioDTO;
import daw.VistaPlus.services.exceptions.AutorNotFoundException;
import daw.VistaPlus.services.exceptions.ObraNotFoundException;
import daw.VistaPlus.services.exceptions.UsuarioNotFoundException;
import daw.VistaPlus.services.mappers.ObraMapper;
import daw.VistaPlus.services.mappers.OpinionMapper;
import daw.VistaPlus.services.mappers.UsuarioMapper;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ObraRepository obraRepository;

    @Autowired
    private OpinionRepository opinionRepository;

    // ─────────────────────────────────────────────
    // CRUD USUARIO
    // ─────────────────────────────────────────────

    public List<UsuarioDTO> findAll() {
        return this.usuarioRepository.findAll().stream()
                .map(UsuarioMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UsuarioDTO findById(int id) {
        Usuario usuario = this.usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado con id: " + id));
        return UsuarioMapper.toDTO(usuario);
    }

    public UsuarioDTO create(UsuarioDTO dto) {
        Usuario usuario = UsuarioMapper.toEntity(dto);
        usuario.setPassword(dto.getPassword());
        if (usuario.getRol() == null) {
            usuario.setRol("USER");
        }
        return UsuarioMapper.toDTO(this.usuarioRepository.save(usuario));
    }

    public UsuarioDTO update(int id, UsuarioDTO dto) {
        Usuario usuarioExistente = this.usuarioRepository.findById(id)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado"));
        usuarioExistente.setUsername(dto.getUsername());
        usuarioExistente.setEmail(dto.getEmail());
        usuarioExistente.setNacionalidad(dto.getNacionalidad());
        usuarioExistente.setFechaNac(dto.getFechaNac());
        usuarioExistente.setRol(dto.getRol());
        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            usuarioExistente.setPassword(dto.getPassword());
        }
        return UsuarioMapper.toDTO(this.usuarioRepository.save(usuarioExistente));
    }

    public void deleteById(int id) {
        if (!this.usuarioRepository.existsById(id)) {
            throw new UsuarioNotFoundException("Usuario no encontrado");
        }
        this.usuarioRepository.deleteById(id);
    }

    public Usuario findByUsername(String username) {
        Usuario usuario = (Usuario) this.usuarioRepository.findByUsername(username);
        if (usuario == null) {
            throw new UsuarioNotFoundException("El usuario " + username + " no existe.");
        }
        return usuario;
    }

    // ─────────────────────────────────────────────
    // ENDPOINTS DE AUTOR (gestión de obras)
    // ─────────────────────────────────────────────

    // POST /usuarios/{autorId}/obras
    public ObraDTO addObra(int autorId, ObraDTO obraDto) {
        Usuario usuario = this.usuarioRepository.findById(autorId)
                .orElseThrow(() -> new AutorNotFoundException("Autor no encontrado con id: " + autorId));

        Obra obra = ObraMapper.toEntity(obraDto);
        obra.setUsuario(usuario);
        obra.setIdAutor(autorId);

        return ObraMapper.toDTO(this.obraRepository.save(obra));
    }

    // PUT /usuarios/{autorId}/obras/{obraId}
    public ObraDTO updateObra(int autorId, int obraId, ObraDTO obraDto) {
        if (!this.usuarioRepository.existsById(autorId)) {
            throw new AutorNotFoundException("Autor no encontrado con id: " + autorId);
        }

        Obra obra = this.obraRepository.findById(obraId)
                .orElseThrow(() -> new ObraNotFoundException("Obra no encontrada con id: " + obraId));

        if (Integer.compare(obra.getUsuario().getId(), autorId) != 0) {
            throw new IllegalArgumentException("Esta obra no pertenece a este autor");
        }

        Obra updatedObra = ObraMapper.toEntity(obraDto);
        updatedObra.setId(obraId);
        updatedObra.setUsuario(obra.getUsuario());
        updatedObra.setIdAutor(autorId);

        return ObraMapper.toDTO(this.obraRepository.save(updatedObra));
    }

    // ─────────────────────────────────────────────
    // ENDPOINTS DE USUARIO (opiniones sobre obras)
    // ─────────────────────────────────────────────

    // POST /usuarios/{usuarioId}/obras/{obraId}/comentario
    public OpinionDTO addComentario(int usuarioId, int obraId, String comentario) {
        Usuario usuario = this.usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado con id: " + usuarioId));
        Obra obra = this.obraRepository.findById(obraId)
                .orElseThrow(() -> new ObraNotFoundException("Obra no encontrada con id: " + obraId));

        Opinion opinion = getOrCreateOpinion(usuario, obra);
        opinion.setComentario(comentario);
        opinion.setFecha(LocalDateTime.now());

        return OpinionMapper.toDTO(this.opinionRepository.save(opinion));
    }

    // POST /usuarios/{usuarioId}/obras/{obraId}/puntuacion
    public OpinionDTO addPuntuacion(int usuarioId, int obraId, int puntuacion) {
        if (puntuacion < 1 || puntuacion > 10) {
            throw new IllegalArgumentException("La puntuacion debe estar entre 1 y 10");
        }

        Usuario usuario = this.usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado con id: " + usuarioId));
        Obra obra = this.obraRepository.findById(obraId)
                .orElseThrow(() -> new ObraNotFoundException("Obra no encontrada con id: " + obraId));

        Opinion opinion = getOrCreateOpinion(usuario, obra);
        opinion.setPuntuacion(puntuacion);
        opinion.setFecha(LocalDateTime.now());

        return OpinionMapper.toDTO(this.opinionRepository.save(opinion));
    }

    // POST /usuarios/{usuarioId}/obras/{obraId}/marcar
    public OpinionDTO marcarObra(int usuarioId, int obraId) {
        Usuario usuario = this.usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado con id: " + usuarioId));
        Obra obra = this.obraRepository.findById(obraId)
                .orElseThrow(() -> new ObraNotFoundException("Obra no encontrada con id: " + obraId));

        Opinion opinion = getOrCreateOpinion(usuario, obra);
        // Toggle: si estaba marcada, desmarca; si no, marca
        opinion.setMarcar(!opinion.isMarcar());
        opinion.setFecha(LocalDateTime.now());

        return OpinionMapper.toDTO(this.opinionRepository.save(opinion));
    }

    // ─────────────────────────────────────────────
    // MÉTODO AUXILIAR
    // Busca la Opinion existente del usuario sobre la obra,
    // o crea una nueva si todavía no existe
    // ─────────────────────────────────────────────
    private Opinion getOrCreateOpinion(Usuario usuario, Obra obra) {
        return this.opinionRepository
                .findByIdUsuarioAndIdObra(usuario.getId(), obra.getId())
                .orElseGet(() -> {
                    Opinion nueva = new Opinion();
                    nueva.setUsuario(usuario);
                    nueva.setObra(obra);
                    nueva.setIdUsuario(usuario.getId());
                    nueva.setIdObra(obra.getId());
                    nueva.setFecha(LocalDateTime.now());
                    return nueva;
                });
    }
}