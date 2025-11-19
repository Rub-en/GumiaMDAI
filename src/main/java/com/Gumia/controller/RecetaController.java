package com.Gumia.controller;

import com.Gumia.model.Receta;
import com.Gumia.service.RecetaService;
import com.Gumia.repositories.UsuarioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/recetas")
public class RecetaController {

    private final RecetaService recetaService;
    private final UsuarioRepository usuarioRepository;

    public RecetaController(RecetaService recetaService, UsuarioRepository usuarioRepository) {
        this.recetaService = recetaService;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public String listarRecetas(Model model) {
        model.addAttribute("recetas", recetaService.listarTodas());
        return "recetas";
    }

    @GetMapping("/nueva")
    public String mostrarFormulario(Model model) {
        model.addAttribute("receta", new Receta());
        model.addAttribute("usuarios", usuarioRepository.findAll());
        return "form_receta";
    }

    @PostMapping("/guardar")
    public String guardarReceta(@ModelAttribute Receta receta) {
        recetaService.guardar(receta);
        return "redirect:/recetas";
    }
}
