package com.tuforo.foro_backend.dto;

import java.time.LocalDateTime;

public class ComentarioDTO {
  public Long id;
  public String contenido;
  public String username;
  public LocalDateTime fechaCreacion;

  public ComentarioDTO(Long id, String contenido, String username, LocalDateTime fechaCreacion) {
      this.id = id;
      this.contenido = contenido;
      this.username = username;
      this.fechaCreacion = fechaCreacion;
  }
}