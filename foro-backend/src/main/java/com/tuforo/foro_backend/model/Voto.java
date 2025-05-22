package com.tuforo.foro_backend.model;

import jakarta.persistence.*;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"usuario_id", "tema_id"})})
public class Voto {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // Relación con Usuario
  @ManyToOne
  @JoinColumn(name = "usuario_id")
  private Usuario usuario;

  // Relación con Tema
  @ManyToOne
  @JoinColumn(name = "tema_id")
  private Tema tema;

  // Tipo de voto: true = positivo, false = negativo
  private boolean positivo;

  public Voto() {}

  public Voto(Usuario usuario, Tema tema, boolean positivo) {
      this.usuario = usuario;
      this.tema = tema;
      this.positivo = positivo;
  }

  // Getters y setters...
  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public Usuario getUsuario() { return usuario; }
  public void setUsuario(Usuario usuario) { this.usuario = usuario; }

  public Tema getTema() { return tema; }
  public void setTema(Tema tema) { this.tema = tema; }

  public boolean isPositivo() { return positivo; }
  public void setPositivo(boolean positivo) { this.positivo = positivo; }
}