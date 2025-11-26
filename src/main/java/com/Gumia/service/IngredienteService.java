package com.Gumia.service;

import com.Gumia.model.Ingrediente;
import com.Gumia.repositories.IngredienteRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IngredienteService {

    private final IngredienteRepository ingredienteRepository;

    public IngredienteService(IngredienteRepository ingredienteRepository) {
        this.ingredienteRepository = ingredienteRepository;
    }

    public Ingrediente guardarIngrediente(Ingrediente ingrediente) {
        return ingredienteRepository.save(ingrediente);
    }

    public void borrarIngrediente(Long id) {
        ingredienteRepository.deleteById(id);
    }

    public Optional<Ingrediente> buscarPorId(Long id) {
        return ingredienteRepository.findById(id);
    }
}