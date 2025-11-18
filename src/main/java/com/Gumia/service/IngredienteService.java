package com.Gumia.service;

import com.Gumia.model.Ingrediente;
import com.Gumia.repositories.IngredienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredienteService {

    private final IngredienteRepository ingredienteRepository;

    public IngredienteService(IngredienteRepository ingredienteRepository) {
        this.ingredienteRepository = ingredienteRepository;
    }

    // Crear ingrediente
    public Ingrediente crearIngrediente(Ingrediente ingrediente) {
        return ingredienteRepository.save(ingrediente);
    }

    // Editar ingrediente
    public Ingrediente editarIngrediente(Ingrediente ingrediente) {
        return ingredienteRepository.save(ingrediente);
    }

    // Eliminar ingrediente
    public void eliminarIngrediente(Long id) {
        ingredienteRepository.deleteById(id);
    }

    // Buscar ingredientes por nombre
    public List<Ingrediente> buscarPorNombre(String nombre) {
        return ingredienteRepository.findByNombre(nombre);
    }

    // Buscar ingredientes de una receta concreta
    public List<Ingrediente> buscarPorReceta(Long recetaId) {
        return ingredienteRepository.findByRecetaId(recetaId);
    }

    // Listar todos los ingredientes
    public List<Ingrediente> listarIngredientes() {
        return ingredienteRepository.findAll();
    }
}
