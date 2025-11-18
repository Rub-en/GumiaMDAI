package com.Gumia.service;

import com.Gumia.model.Receta;
import com.Gumia.repositories.RecetaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecetaService {

    private final RecetaRepository recetaRepository;

    public RecetaService(RecetaRepository recetaRepository) {
        this.recetaRepository = recetaRepository;
    }

    public Receta crearReceta(Receta receta) {
        return recetaRepository.save(receta);
    }

    public Receta editarReceta(Receta receta) {
        return recetaRepository.save(receta);
    }

    public void eliminarReceta(Long id) {
        recetaRepository.deleteById(id);
    }

    public Receta buscarPorId(Long id) {
        return recetaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Receta no encontrada"));
    }

    public List<Receta> listarRecetas() {
        return recetaRepository.findAll();
    }
}
