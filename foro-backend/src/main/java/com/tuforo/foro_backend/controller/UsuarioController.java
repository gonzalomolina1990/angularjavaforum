package com.tuforo.foro_backend.controller;

import com.tuforo.foro_backend.model.Usuario;
import com.tuforo.foro_backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
          return ResponseEntity.ok(new UsuarioDTO(usuarioEnBD.getId(), usuarioEnBD.getUsername()));
      } else {
          return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Contraseña incorrecta");
      }
  } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
  }
}

// DTO interno
public static class UsuarioDTO {
  public Long id;
  public String username;

  public UsuarioDTO(Long id, String username) {
      this.id = id;
      this.username = username;
  }
}

 @PostMapping("/registro")
    public Usuario registrarUsuario(@RequestBody Usuario usuario) {
    return usuarioRepository.save(usuario);
    }

}