package com.tuforo.foro_backend.dto;

import java.time.LocalDateTime;

public class TemaDTO {
  public Long id;
  public String titulo;
  public String contenido;
  public String username;
  public LocalDateTime fechaCreacion;
  public int votosPositivos;
  public int votosNegativos;

  public TemaDTO(Long id, String titulo, String contenido, String username, LocalDateTime fechaCreacion, int votosPositivos, int votosNegativos) {
      this.id = id;
      this.titulo = titulo;
      this.contenido = contenido;
      this.username = username;
      this.fechaCreacion = fechaCreacion;
      this.votosPositivos = votosPositivos;
      this.votosNegativos = votosNegativos;
  }
}