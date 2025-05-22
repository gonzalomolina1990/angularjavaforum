package com.tuforo.foro_backend.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Tema {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String titulo;

  @Column(nullable = false, length = 1000)
  private String contenido;

  @Column(nullable = false, updatable = false)
  private LocalDateTime fechaCreacion;

  @PrePersist
  protected void onCreate() {
    this.fechaCreacion = LocalDateTime.now();
  }

  // Relación con Usuario (autor del tema)
  @ManyToOne
  @JoinColumn(name = "usuario_id")
  private Usuario usuario;

  @Column(nullable = false)
  private int votosPositivos = 0;

  @Column(nullable = false)
  private int votosNegativos = 0;

  public Tema() {}

  public Tema(String titulo, String contenido, Usuario usuario) {
      this.titulo = titulo;
      this.contenido = contenido;
      this.usuario = usuario;
  }

  // Getters y setters
  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public String getTitulo() { return titulo; }
  public void setTitulo(String titulo) { this.titulo = titulo; }

  public String getContenido() { return contenido; }
  public void setContenido(String contenido) { this.contenido = contenido; }

  public Usuario getUsuario() { return usuario; }
  public void setUsuario(Usuario usuario) { this.usuario = usuario; }

  public int getVotosPositivos() { return votosPositivos; }
  public void setVotosPositivos(int votosPositivos) { this.votosPositivos = votosPositivos; }

  public int getVotosNegativos() { return votosNegativos; }
  public void setVotosNegativos(int votosNegativos) { this.votosNegativos = votosNegativos; }

  public LocalDateTime getFechaCreacion() { return fechaCreacion; }


}