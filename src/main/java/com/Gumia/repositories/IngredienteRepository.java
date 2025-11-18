package com.Gumia.repositories;

import com.Gumia.model.Ingrediente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IngredienteRepository extends JpaRepository<Ingrediente, Long> {

    // Buscar ingredientes por nombre exacto
    List<Ingrediente> findByNombre(String nombre);

    // Buscar todos los ingredientes que pertenecen a una receta concreta (filtrando por ID de receta)
    List<Ingrediente> findByRecetaId(Long recetaId);
}
