package com.Gumia.repositories;

import com.Gumia.model.Receta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface RecetaRepository extends JpaRepository<Receta, Long> {
    Page<Receta> findByTituloContainingIgnoreCase(String q, PageRequest pageable);
}