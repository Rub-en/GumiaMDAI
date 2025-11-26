package com.Gumia.controller;

import com.Gumia.service.RecetaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    private final RecetaService recetaService;

    public IndexController(RecetaService recetaService) {
        this.recetaService = recetaService;
    }

    @GetMapping("/")
    public String home(Model model) {
        // Carga todas las recetas para la portada
        model.addAttribute("recetas", recetaService.listarTodas());
        return "index";
    }
}