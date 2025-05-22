package com.tuforo.foro_backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Comentario {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 1000)
  private String contenido;

  @Column(nullable = false, updatable = false)
  private LocalDateTime fechaCreacion;

  @PrePersist
  protected void onCreate() {
    this.fechaCreacion = LocalDateTime.now();
  }

  // Relación con Tema
  @ManyToOne
  @JoinColumn(name = "tema_id")
  private Tema tema;

  // Relación con Usuario (autor del comentario)
  @ManyToOne
  @JoinColumn(name = "usuario_id")
  private Usuario usuario;

  public Comentario() {}

  public Comentario(String contenido, Tema tema, Usuario usuario) {
      this.contenido = contenido;
      this.tema = tema;
      this.usuario = usuario;
  }

  // Getters y setters
  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public String getContenido() { return contenido; }
  public void setContenido(String contenido) { this.contenido = contenido; }

  public Tema getTema() { return tema; }
  public void setTema(Tema tema) { this.tema = tema; }

  public Usuario getUsuario() { return usuario; }
  public void setUsuario(Usuario usuario) { this.usuario = usuario; }

  public LocalDateTime getFechaCreacion() { return fechaCreacion; }

}