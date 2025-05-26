package com.tuforo.foro_backend.controller;

import com.tuforo.foro_backend.model.Usuario;
import com.tuforo.foro_backend.repository.UsuarioRepository;
import com.tuforo.foro_backend.dto.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;


@CrossOrigin(origins = "https://angularjavaforum.vercel.app")
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

 @Autowired
 private UsuarioRepository usuarioRepository;

@PostMapping("/login")
public ResponseEntity<?> login(@RequestBody Usuario usuario) {
  Optional<Usuario> usuarioOptional = usuarioRepository.findByUsername(usuario.getUsername());
  if (usuarioOptional.isPresent()) {
      Usuario usuarioEnBD = usuarioOptional.get();
      if (usuarioEnBD.getPassword().equals(usuario.getPassword())) {
          // Devuelve SOLO id y username
          return ResponseEntity.ok(new UsuarioDTO(usuarioEnBD.getId(), usuarioEnBD.getUsername(), usuarioEnBD.getRol()));
      } else {
          return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Contraseña incorrecta");
      }
  } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
  }
}

@GetMapping("/todos")
public List<UsuarioDTO> listarUsuarios() {
  return usuarioRepository.findAll().stream()
      .map(u -> new UsuarioDTO(u.getId(), u.getUsername(), u.getRol()))
      .collect(Collectors.toList());
}

@PostMapping("/registro")
public ResponseEntity<?> registrarUsuario(@RequestBody Usuario usuario) {
    // Asigna rol USER por defecto no viene
    if (usuario.getRol() == null || usuario.getRol().isEmpty()) {
        usuario.setRol("USER");
    }
    Usuario nuevoUsuario = usuarioRepository.save(usuario);
    return ResponseEntity.ok(new UsuarioDTO(
        nuevoUsuario.getId(),
        nuevoUsuario.getUsername(),
        nuevoUsuario.getRol()
    ));
}

// ELIMINAR USUARIO
@DeleteMapping("/{id}")
public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) {
    if (!usuarioRepository.existsById(id)) {
        return ResponseEntity.notFound().build();
    }
    usuarioRepository.deleteById(id);
    return ResponseEntity.ok(" eliminado");
}

// Editar usuario
@PutMapping("/{id}")
public ResponseEntity<?> editarUsuario(@PathVariable Long id, @RequestBody Usuario datos) {
    Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
    if (usuarioOpt.isPresent()) {
        Usuario u = usuarioOpt.get();
        u.setUsername(datos.getUsername());
        u.setPassword(datos.getPassword());
        u.setRol(datos.getRol());
        usuarioRepository.save(u);
        return ResponseEntity.ok(new UsuarioDTO(
            u.getId(),
            u.getUsername(),
            u.getRol()
        ));
    } else {
        return ResponseEntity.notFound().build();
    }
}


}