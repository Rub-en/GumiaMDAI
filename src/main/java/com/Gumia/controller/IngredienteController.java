package com.Gumia.controller;

import com.Gumia.service.IngredienteService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class IngredienteController {

    private final IngredienteService ingredienteService;

    public IngredienteController(IngredienteService ingredienteService) {
        this.ingredienteService = ingredienteService;
    }

    @GetMapping("/ingrediente/borrar/{id}")
    public String borrarIngrediente(@PathVariable Long id) {
        ingredienteService.borrarIngrediente(id);
        // Redirige atrás (en una app real redirigiría a editar receta)
        return "redirect:/perfil";
    }
}