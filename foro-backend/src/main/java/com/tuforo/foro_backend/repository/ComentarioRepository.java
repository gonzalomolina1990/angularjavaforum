package com.tuforo.foro_backend.repository;

import com.tuforo.foro_backend.model.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
  List<Comentario> findByTemaId(Long temaId);
}