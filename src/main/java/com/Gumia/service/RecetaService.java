package com.Gumia.service;

import com.Gumia.model.Receta;
import com.Gumia.model.Usuario;
import com.Gumia.repositories.RecetaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RecetaService {

    private final RecetaRepository recetaRepository;

    public RecetaService(RecetaRepository recetaRepository) {
        this.recetaRepository = recetaRepository;
    }

    // 1. Crear y Editar Receta
    // CAMBIO: Ahora es void para evitar el warning "Return value is never used"
    @Transactional
    public void guardarReceta(Receta receta, Usuario autor) {
        // Si es nueva (id null), asignamos el autor.
        // Si es edición, el autor ya viene seteado o se mantiene el original.
        if (receta.getId() == null) {
            receta.setAutor(autor);
        }
        recetaRepository.save(receta);
    }

    // 2. Borrar Receta
    public void borrarReceta(Long id) {
        if (recetaRepository.existsById(id)) {
            recetaRepository.deleteById(id);
        } else {
            throw new RuntimeException("Receta no encontrada");
        }
    }

    // 3. Consultas básicas
    public List<Receta> listarTodas() {
        return recetaRepository.findAll();
    }

    public Optional<Receta> buscarPorId(Long id) {
        return recetaRepository.findById(id);
    }

    // 4. Filtros (Requisitos del README)
    public List<Receta> buscarPorCategoria(String categoria) {
        return recetaRepository.findByCategoria(categoria);
    }

    public List<Receta> buscarPorDificultad(int dificultad) {
        return recetaRepository.findByDificultad(dificultad);
    }

    // 5. Buscador Avanzado (Por título con paginación)
    public Page<Receta> buscarPorTitulo(String titulo, Pageable pageable) {
        return recetaRepository.findByTituloContainingIgnoreCase(titulo, pageable);
    }
}