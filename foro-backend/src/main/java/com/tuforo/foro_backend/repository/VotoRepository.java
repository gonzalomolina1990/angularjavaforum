package com.tuforo.foro_backend.repository;

import com.tuforo.foro_backend.model.Voto;
import com.tuforo.foro_backend.model.Usuario;
import com.tuforo.foro_backend.model.Tema;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VotoRepository extends JpaRepository<Voto, Long> {
  Optional<Voto> findByUsuarioAndTema(Usuario usuario, Tema tema);
  int countByTemaAndPositivo(Tema tema, boolean positivo);
}