package com.Gumia.controller;

import com.Gumia.model.Receta;
import com.Gumia.service.RecetaService;
import com.Gumia.repositories.RecetaRepository;
import com.Gumia.repositories.UsuarioRepository;
import org.springframework.data.domain.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/recetas")
public class RecetaController {

    private final RecetaService recetaService;
    private final UsuarioRepository usuarioRepository;
    private final RecetaRepository recetaRepository;

    public RecetaController(RecetaService recetaService, UsuarioRepository usuarioRepository, RecetaRepository recetaRepository) {
        this.recetaService = recetaService;
        this.usuarioRepository = usuarioRepository;
        this.recetaRepository = recetaRepository;
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

    @GetMapping("/")
    public String index(@RequestParam(required = false) String q, Model model) {
        if (q != null && !q.isBlank()) {
            // Mostrar hasta 100 coincidencias iniciales en la vista (puede cargarse más desde la API)
            Page<Receta> page = recetaRepository.findByTituloContainingIgnoreCase(q, PageRequest.of(0, 100, Sort.by("id").descending()));
            model.addAttribute("resultados", page.getContent());
            model.addAttribute("q", q);
        } else {
            PageRequest pr = PageRequest.of(0, 5, Sort.by("id").descending());
            Page<Receta> destacados = recetaRepository.findAll(pr);
            model.addAttribute("destacadas", destacados.getContent());
        }
        return "index";
    }

    @GetMapping("/api/recetas")
    @ResponseBody
    public List<Receta> apiRecetas(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "100") int size,
                                   @RequestParam(required = false) String q) {
        PageRequest pageable = PageRequest.of(Math.max(0, page), Math.max(1, size), Sort.by("id").descending());
        Page<Receta> result;
        if (q != null && !q.isBlank()) {
            result = recetaRepository.findByTituloContainingIgnoreCase(q, pageable);
        } else {
            result = recetaRepository.findAll(pageable);
        }
        return result.getContent();
    }
}
