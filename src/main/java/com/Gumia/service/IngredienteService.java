package com.Gumia.service;

import com.Gumia.model.Ingrediente;
import com.Gumia.repositories.IngredienteRepository;
import org.springframework.stereotype.Service;

@Service
public class IngredienteService {

    private final IngredienteRepository ingredienteRepository;

    public IngredienteService(IngredienteRepository ingredienteRepository) {
        this.ingredienteRepository = ingredienteRepository;
    }

    public Iterable<Ingrediente> listarTodos() {
        return ingredienteRepository.findAll();
    }

    public void guardar(Ingrediente ingrediente) {
        ingredienteRepository.save(ingrediente);
    }
}
