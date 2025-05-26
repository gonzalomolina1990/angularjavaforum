package com.tuforo.foro_backend.dto;

public class UsuarioDTO {
 public Long id;    
 public String username;
 public String rol;

 public UsuarioDTO(Long id, String username, String rol) {
     this.id = id;
     this.username = username;
     this.rol = rol;
 }
}