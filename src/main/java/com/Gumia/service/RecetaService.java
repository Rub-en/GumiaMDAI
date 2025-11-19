package com.Gumia.service;

import com.Gumia.model.Receta;
import com.Gumia.repositories.RecetaRepository;
import org.springframework.stereotype.Service;

@Service
public class RecetaService {

    private final RecetaRepository recetaRepository;

    public RecetaService(RecetaRepository recetaRepository) {
        this.recetaRepository = recetaRepository;
    }

    public Iterable<Receta> listarTodas() {
        return recetaRepository.findAll();
    }

    public void guardar(Receta receta) {
        recetaRepository.save(receta);
    }
}
