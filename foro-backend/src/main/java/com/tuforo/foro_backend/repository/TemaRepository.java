package com.tuforo.foro_backend.repository;

import com.tuforo.foro_backend.model.Tema;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TemaRepository extends JpaRepository<Tema, Long> {
      List<Tema> findAllByOrderByFechaCreacionDesc();
}