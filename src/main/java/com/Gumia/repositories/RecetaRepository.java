package com.Gumia.repositories;

import com.Gumia.model.Receta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecetaRepository extends JpaRepository<Receta, Long> {

    // Buscar recetas por titulo exacto
    List<Receta> findByTitulo(String titulo);

    // Buscar recetas por categoria (ejemplo: "Postre", "Pasta")
    List<Receta> findByCategoria(String categoria);

    // Buscar recetas por dificultad (ejemplo: "Facil", "Media", "Dificil")
    List<Receta> findByDificultad(String dificultad);

    // Buscar recetas cuyo titulo contenga la cadena indicada, ignorando mayusculas/minusculas
    List<Receta> findByTituloContainingIgnoreCase(String titulo);
}
