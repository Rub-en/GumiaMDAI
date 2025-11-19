package com.Gumia.controller;

import com.Gumia.model.Ingrediente;
import com.Gumia.service.IngredienteService;
import com.Gumia.repositories.RecetaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ingredientes")
public class IngredienteController {

    private final IngredienteService ingredienteService;
    private final RecetaRepository recetaRepository;

    public IngredienteController(IngredienteService ingredienteService, RecetaRepository recetaRepository) {
        this.ingredienteService = ingredienteService;
        this.recetaRepository = recetaRepository;
    }

    @GetMapping
    public String listarIngredientes(Model model) {
        model.addAttribute("ingredientes", ingredienteService.listarTodos());
        return "ingredientes";
    }

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("ingrediente", new Ingrediente());
        model.addAttribute("recetas", recetaRepository.findAll());
        return "form_ingrediente";
    }

    @PostMapping("/guardar")
    public String guardarIngrediente(@ModelAttribute Ingrediente ingrediente) {
        ingredienteService.guardar(ingrediente);
        return "redirect:/ingredientes";
    }
}
