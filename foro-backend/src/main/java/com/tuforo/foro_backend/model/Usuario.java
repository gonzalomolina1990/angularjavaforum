package com.tuforo.foro_backend.model;

import jakarta.persistence.*;

@Entity
public class Usuario {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String username;

  @Column(nullable = false)
  private String password;

    @Column(nullable = false)
    private String rol = "USER"; // Puede ser "USER" o "ADMIN"


  // Constructor vacío (requerido por JPA)
  public Usuario() {}

  // Constructor con parámetros (opcional)
  public Usuario(String username, String password, String rol) {
      this.username = username;
      this.password = password;
      this.rol = rol;

  }

  // Getters y setters
  public Long getId() { return id; }
  public void setId(Long id) { this.id = id; }

  public String getUsername() { return username; }
  public void setUsername(String username) { this.username = username; }

  public String getPassword() { return password; }
  public void setPassword(String password) { this.password = password; }

  public String getRol() { return rol; }
  public void setRol(String rol) { this.rol = rol; }
}