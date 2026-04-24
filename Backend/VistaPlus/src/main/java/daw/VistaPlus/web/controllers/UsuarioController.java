package daw.VistaPlus.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import daw.VistaPlus.services.UsuarioService;
import daw.VistaPlus.services.dto.ObraDTO;
import daw.VistaPlus.services.dto.UsuarioDTO;
import daw.VistaPlus.services.exceptions.AutorNotFoundException;
import daw.VistaPlus.services.exceptions.ObraNotFoundException;
import daw.VistaPlus.services.exceptions.UsuarioNotFoundException;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // ─────────────────────────────────────────────
    // CRUD USUARIO
    // ─────────────────────────────────────────────

    // GET /usuario
    @GetMapping
    public ResponseEntity<?> findAll() {
        try {
            List<UsuarioDTO> usuarios = this.usuarioService.findAll();
            return ResponseEntity.ok(usuarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener los usuarios: " + e.getMessage());
        }
    }

    // GET /usuario/{id}
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable int id) {
        try {
            return ResponseEntity.ok(this.usuarioService.findById(id));
        } catch (UsuarioNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener el usuario: " + e.getMessage());
        }
    }

    // POST /usuario
    @PostMapping
    public ResponseEntity<?> create(@RequestBody UsuarioDTO dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(this.usuarioService.create(dto));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear el usuario: " + e.getMessage());
        }
    }

    // PUT /usuario/{id}
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody UsuarioDTO dto) {
        try {
            return ResponseEntity.ok(this.usuarioService.update(id, dto));
        } catch (UsuarioNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar el usuario: " + e.getMessage());
        }
    }

    // DELETE /usuario/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable int id) {
        try {
            this.usuarioService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (UsuarioNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al eliminar el usuario: " + e.getMessage());
        }
    }

    // ─────────────────────────────────────────────
    // ENDPOINTS DE AUTOR (gestión de sus obras)
    // ─────────────────────────────────────────────

    // POST /usuario/{usuarioId}/obras
    @PostMapping("/{usuarioId}/obras")
    public ResponseEntity<?> addObra(
            @PathVariable int usuarioId,
            @RequestBody ObraDTO obraDto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(this.usuarioService.addObra(usuarioId, obraDto));
        } catch (AutorNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear la obra: " + e.getMessage());
        }
    }

    // PUT /usuario/{usuarioId}/obras/{obraId}
    @PutMapping("/{usuarioId}/obras/{obraId}")
    public ResponseEntity<?> updateObra(
            @PathVariable int usuarioId,
            @PathVariable int obraId,
            @RequestBody ObraDTO obraDto) {
        try {
            return ResponseEntity.ok(this.usuarioService.updateObra(usuarioId, obraId, obraDto));
        } catch (AutorNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (ObraNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al actualizar la obra: " + e.getMessage());
        }
    }

    // ─────────────────────────────────────────────
    // ENDPOINTS DE USUARIO (opiniones sobre obras)
    // ─────────────────────────────────────────────

    // POST /usuario/{usuarioId}/obras/{obraId}/comentario
    @PostMapping("/{usuarioId}/obras/{obraId}/comentario")
    public ResponseEntity<?> addComentario(
            @PathVariable int usuarioId,
            @PathVariable int obraId,
            @RequestBody String comentario) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(this.usuarioService.addComentario(usuarioId, obraId, comentario));
        } catch (UsuarioNotFoundException | ObraNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al añadir el comentario: " + e.getMessage());
        }
    }

    // POST /usuario/{usuarioId}/obras/{obraId}/puntuacion
    @PostMapping("/{usuarioId}/obras/{obraId}/puntuacion")
    public ResponseEntity<?> addPuntuacion(
            @PathVariable int usuarioId,
            @PathVariable int obraId,
            @RequestBody int puntuacion) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(this.usuarioService.addPuntuacion(usuarioId, obraId, puntuacion));
        } catch (UsuarioNotFoundException | ObraNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al añadir la puntuacion: " + e.getMessage());
        }
    }

    // POST /usuario/{usuarioId}/obras/{obraId}/marcar
    @PostMapping("/{usuarioId}/obras/{obraId}/marcar")
    public ResponseEntity<?> marcarObra(
            @PathVariable int usuarioId,
            @PathVariable int obraId) {
        try {
            return ResponseEntity.ok(this.usuarioService.marcarObra(usuarioId, obraId));
        } catch (UsuarioNotFoundException | ObraNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al marcar la obra: " + e.getMessage());
        }
    }
}