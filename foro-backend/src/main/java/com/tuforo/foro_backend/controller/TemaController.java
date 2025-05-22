package com.tuforo.foro_backend.controller;

import com.tuforo.foro_backend.model.Tema;
import com.tuforo.foro_backend.model.Usuario;
import com.tuforo.foro_backend.repository.TemaRepository;
import com.tuforo.foro_backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.tuforo.foro_backend.model.Voto;
import com.tuforo.foro_backend.repository.VotoRepository;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;
import com.tuforo.foro_backend.dto.TemaDTO;



@RestController
@RequestMapping("/api/temas")
public class TemaController {

  @Autowired
  private TemaRepository temaRepository;

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  private VotoRepository votoRepository;

  @PostMapping("/crear")
  public Tema crearTema(@RequestBody TemaRequest request) {
      Optional<Usuario> usuarioOpt = usuarioRepository.findById(request.usuarioId);
      if (usuarioOpt.isPresent()) {
          Tema tema = new Tema(request.titulo, request.contenido, usuarioOpt.get());
          return temaRepository.save(tema);
      } else {
          throw new RuntimeException("Usuario no encontrado");
      }
  }

  @GetMapping("/todos")
  public List<TemaDTO> listarTemas() {
    return temaRepository.findAll().stream()
        .map(tema -> new TemaDTO(
            tema.getId(),
            tema.getTitulo(),
            tema.getContenido(),
            tema.getUsuario().getUsername(),
            tema.getFechaCreacion()
        ))
        .collect(Collectors.toList());
  }

// Recibe el ID del usuario que vota
@PostMapping("/{id}/votar-positivo")
public ResponseEntity<?> votarPositivo(@PathVariable Long id, @RequestParam Long usuarioId) {
  Tema tema = temaRepository.findById(id)
          .orElseThrow(() -> new RuntimeException("Tema no encontrado"));
  Usuario usuario = usuarioRepository.findById(usuarioId)
          .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

  // ¿Ya votó este usuario este tema?
  if (votoRepository.findByUsuarioAndTema(usuario, tema).isPresent()) {
      return ResponseEntity.badRequest().body("Ya votaste este tema.");
  }

  Voto voto = new Voto(usuario, tema, true);
  votoRepository.save(voto);

  // Actualiza los contadores (opcional, puedes calcularlos dinámicamente si prefieres)
  tema.setVotosPositivos(votoRepository.countByTemaAndPositivo(tema, true));
  tema.setVotosNegativos(votoRepository.countByTemaAndPositivo(tema, false));
  temaRepository.save(tema);

  return ResponseEntity.ok(tema);
}

@PostMapping("/{id}/votar-negativo")
public ResponseEntity<?> votarNegativo(@PathVariable Long id, @RequestParam Long usuarioId) {
  Tema tema = temaRepository.findById(id)
          .orElseThrow(() -> new RuntimeException("Tema no encontrado"));
  Usuario usuario = usuarioRepository.findById(usuarioId)
          .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

  if (votoRepository.findByUsuarioAndTema(usuario, tema).isPresent()) {
      return ResponseEntity.badRequest().body("Ya votaste este tema.");
  }

  Voto voto = new Voto(usuario, tema, false);
  votoRepository.save(voto);

  tema.setVotosPositivos(votoRepository.countByTemaAndPositivo(tema, true));
  tema.setVotosNegativos(votoRepository.countByTemaAndPositivo(tema, false));
  temaRepository.save(tema);

  return ResponseEntity.ok(tema);
}

  // Clase interna para recibir el JSON
  public static class TemaRequest {
      public String titulo;
      public String contenido;
      public Long usuarioId;
  }
}