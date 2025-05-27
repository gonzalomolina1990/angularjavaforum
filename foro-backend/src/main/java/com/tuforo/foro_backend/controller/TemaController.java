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


@CrossOrigin(origins = "https://angularjavaforum.vercel.app")
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
  return temaRepository.findAll().stream().map(tema -> {
  String username = tema.getUsuario() != null ? tema.getUsuario().getUsername() : "Desconocido";
      int positivos = votoRepository.countByTemaAndPositivo(tema, true);
      int negativos = votoRepository.countByTemaAndPositivo(tema, false);
      return new TemaDTO(
          tema.getId(),
          tema.getTitulo(),
          tema.getContenido(),
          username,
          tema.getFechaCreacion(),
          positivos,
          negativos
      );
  }).collect(Collectors.toList());
}

// Recibe el ID del usuario que vota
@PostMapping("/{id}/votar-positivo")
public ResponseEntity<?> votarPositivo(@PathVariable Long id, @RequestParam Long usuarioId) {
  Tema tema = temaRepository.findById(id)
          .orElseThrow(() -> new RuntimeException("Tema no encontrado"));
  Usuario usuario = usuarioRepository.findById(usuarioId)
          .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));


   Optional<Voto> votoOpt = votoRepository.findByUsuarioAndTema(usuario, tema);
   if (votoOpt.isPresent()) {
       Voto voto = votoOpt.get();
       if (voto.isPositivo()) {
           // Ya es positivo, hace nada
           return ResponseEntity.badRequest().body("Ya votaste positivo este tema.");
       } else {
           // Cambia el voto a negativo
           voto.setPositivo(false);
           votoRepository.save(voto);
       }
   } else {
       // No había voto, crea uno
       Voto voto = new Voto(usuario, tema, true);
       votoRepository.save(voto);
   }


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

   Optional<Voto> votoOpt = votoRepository.findByUsuarioAndTema(usuario, tema);
   if (votoOpt.isPresent()) {
       Voto voto = votoOpt.get();
       if (!voto.isPositivo()) {
           // Ya es negativo, hace nada
           return ResponseEntity.badRequest().body("Ya votaste negativo este tema.");
       } else {
           // Cambia el voto a positivo
           voto.setPositivo(true);
           votoRepository.save(voto);
       }
   } else {
       // No había voto, crea uno
       Voto voto = new Voto(usuario, tema, false);
       votoRepository.save(voto);
   }

  tema.setVotosPositivos(votoRepository.countByTemaAndPositivo(tema, true));
  tema.setVotosNegativos(votoRepository.countByTemaAndPositivo(tema, false));
  temaRepository.save(tema);

  return ResponseEntity.ok(tema);
}

// Eliminar tema
@DeleteMapping("/{id}")
public ResponseEntity<?> eliminarTema(@PathVariable Long id) {
    if (!temaRepository.existsById(id)) {
        return ResponseEntity.notFound().build();
    }
    temaRepository.deleteById(id);
    return ResponseEntity.ok("Tema eliminado");
}

// Editar tema    
@PutMapping("/{id}")
public ResponseEntity<?> editarTema(@PathVariable Long id, @RequestBody Tema datos) {
Optional<Tema> temaOpt = temaRepository.findById(id);
if (temaOpt.isPresent()) {
    Tema t = temaOpt.get();
    t.setTitulo(datos.getTitulo());
    t.setContenido(datos.getContenido());
    temaRepository.save(t);
    return ResponseEntity.ok(t);
} {
    return ResponseEntity.notFound().build();
}
}

  // Clase interna para recibir el JSON
  public static class TemaRequest {
      public String titulo;
      public String contenido;
      public Long usuarioId;
  }
}