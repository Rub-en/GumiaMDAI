package com.Gumia.controller;

import com.Gumia.model.Ingrediente;
import com.Gumia.service.IngredienteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingredientes")
public class IngredienteController {

    private final IngredienteService ingredienteService;

    public IngredienteController(IngredienteService ingredienteService) {
        this.ingredienteService = ingredienteService;
    }

    // Crear ingrediente
    @PostMapping
    public Ingrediente crearIngrediente(@RequestBody Ingrediente ingrediente) {
        return ingredienteService.crearIngrediente(ingrediente);
    }

    // Editar ingrediente
    @PutMapping
    public Ingrediente editarIngrediente(@RequestBody Ingrediente ingrediente) {
        return ingredienteService.editarIngrediente(ingrediente);
    }

    // Eliminar ingrediente
    @DeleteMapping("/{id}")
    public void eliminarIngrediente(@PathVariable Long id) {
        ingredienteService.eliminarIngrediente(id);
    }

    // Buscar ingredientes por nombre
    @GetMapping("/nombre/{nombre}")
    public List<Ingrediente> buscarPorNombre(@PathVariable String nombre) {
        return ingredienteService.buscarPorNombre(nombre);
    }

    // Buscar ingredientes de una receta concreta
    @GetMapping("/receta/{recetaId}")
    public List<Ingrediente> buscarPorReceta(@PathVariable Long recetaId) {
        return ingredienteService.buscarPorReceta(recetaId);
    }

    // Listar todos los ingredientes
    @GetMapping
    public List<Ingrediente> listarIngredientes() {
        return ingredienteService.listarIngredientes();
    }
}
