// src/main/java/com/Gumia/controller/IndexController.java
package com.Gumia.controller;

import com.Gumia.model.Receta;
import com.Gumia.repositories.RecetaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class IndexController {

    private static final Logger LOG = Logger.getLogger(IndexController.class.getName());
    private final RecetaRepository recetaRepository;

    public IndexController(RecetaRepository recetaRepository) {
        this.recetaRepository = recetaRepository;
    }

    @GetMapping("/")
    public String index(@RequestParam(value = "q", required = false) String q, Model model) {
        String query = q == null ? "" : q.trim();
        List<Receta> resultados = Collections.emptyList();
        List<Receta> destacadas = Collections.emptyList();

        try {
            Page<Receta> page = recetaRepository.findByTituloContainingIgnoreCase(query, PageRequest.of(0, 10));
            if (page != null) resultados = page.getContent();
        } catch (Exception e) {
            LOG.log(Level.WARNING, "Error al buscar recetas por título", e);
        }

        try {
            destacadas = recetaRepository.findAll(PageRequest.of(0, 5)).getContent();
        } catch (Exception e) {
            LOG.log(Level.WARNING, "Error al cargar recetas destacadas", e);
        }

        model.addAttribute("resultados", resultados);
        model.addAttribute("destacadas", destacadas);
        model.addAttribute("q", q == null ? "" : q);
        return "index";
    }
}
