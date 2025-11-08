package com.Gumia.repositories;

import com.Gumia.model.Receta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecetaRepository extends JpaRepository<Receta, Long> {
    public Receta findByNombre(String nombre);
    public Receta sortByDificultadAsc();
    public Receta sortByDificultadDesc();
}
