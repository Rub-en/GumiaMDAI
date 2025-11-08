package com.Gumia.repositories;

import com.Gumia.model.Ingrediente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredienteRepository extends JpaRepository<Ingrediente, Long> {
    public Ingrediente findByNombre(String nombre);
}
