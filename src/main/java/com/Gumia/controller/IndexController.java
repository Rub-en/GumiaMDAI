// src/main/java/com/Gumia/controller/IndexController.java
package com.Gumia.controller;

import com.Gumia.model.Receta;
import com.Gumia.repositories.RecetaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IndexController {

    private final RecetaRepository recetaRepository;

    public IndexController(RecetaRepository recetaRepository) {
        this.recetaRepository = recetaRepository;
    }

    @GetMapping({"/", "/index"})
    public String index(Model model) {
        List<Receta> recetas = recetaRepository.findAll(); // o un método que limite a 5
        model.addAttribute("recetas", recetas);
        return "index";
    }
}
