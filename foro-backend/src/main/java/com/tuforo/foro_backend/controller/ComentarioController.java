package com.tuforo.foro_backend.controller;

import com.tuforo.foro_backend.model.Comentario;
import com.tuforo.foro_backend.model.Tema;
import com.tuforo.foro_backend.model.Usuario;
import com.tuforo.foro_backend.repository.ComentarioRepository;
import com.tuforo.foro_backend.repository.TemaRepository;
import com.tuforo.foro_backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.tuforo.foro_backend.dto.ComentarioDTO;
import java.util.stream.Collectors;
import java.util.Optional;
import java.util.List;

@CrossOrigin(origins = "https://angularjavaforum.vercel.app")
@RestController
@RequestMapping("/api/comentarios")
public class ComentarioController {

  @Autowired
  private ComentarioRepository comentarioRepository;

  @Autowired
  private TemaRepository temaRepository;

  @Autowired
  private UsuarioRepository usuarioRepository;

  // Crear un comentario
  @PostMapping("/crear")
  public Comentario crearComentario(@RequestBody ComentarioRequest request) {
      Optional<Tema> temaOpt = temaRepository.findById(request.temaId);
      Optional<Usuario> usuarioOpt = usuarioRepository.findById(request.usuarioId);

      if (temaOpt.isPresent() && usuarioOpt.isPresent()) {
          Comentario comentario = new Comentario(request.contenido, temaOpt.get(), usuarioOpt.get());
          return comentarioRepository.save(comentario);
      } else {
          throw new RuntimeException("Tema o usuario no encontrado");
      }
  }

  // Listar comentarios de un tema
    @GetMapping("/por-tema/{temaId}")
    public List<ComentarioDTO> listarComentariosPorTema(@PathVariable Long temaId) {
        return comentarioRepository.findByTemaId(temaId).stream()
            .map(comentario -> new ComentarioDTO(
                comentario.getId(),
                comentario.getContenido(),
                comentario.getUsuario().getUsername(),
                comentario.getFechaCreacion()
            ))
            .collect(Collectors.toList());
    }

  // Clase interna para recibir el JSON
  public static class ComentarioRequest {
      public String contenido;
      public Long temaId;
      public Long usuarioId;
  }
}