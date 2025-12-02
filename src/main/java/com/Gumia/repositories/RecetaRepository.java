package com.Gumia.repositories;

import com.Gumia.model.Receta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecetaRepository extends JpaRepository<Receta, Long> {

    // Búsqueda por título (para el buscador avanzado)
    Page<Receta> findDistinctByTituloContainingIgnoreCaseOrIngredientes_NombreContainingIgnoreCase(String titulo, String ingrediente, Pageable pageable);

    // Filtro por Categoría (para la Home o listados)
    List<Receta> findByCategoria(String categoria);

    // Filtro por Dificultad (para los filtros avanzados)
    List<Receta> findByDificultad(int dificultad);
}