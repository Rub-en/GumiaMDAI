package com.Gumia.controller;

import com.Gumia.model.Receta;
import com.Gumia.service.RecetaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/recetas")
public class RecetaController {

    private final RecetaService recetaService;

    public RecetaController(RecetaService recetaService) {
        this.recetaService = recetaService;
    }

    @GetMapping
    public String listarRecetas(Model model) {
        List<Receta> recetas = recetaService.listarRecetas();
        model.addAttribute("recetas", recetas);
        return "recetas"; // templates/recetas.html
    }

    @GetMapping("/nueva")
    public String mostrarFormulario(Model model) {
        model.addAttribute("receta", new Receta());
        return "receta-form"; // templates/receta-form.html
    }

    @GetMapping("/{id}/editar")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model) {
        Receta receta = recetaService.buscarPorId(id);
        model.addAttribute("receta", receta);
        return "receta-form"; // templates/receta-form.html
    }

    @PostMapping
    public String guardarOEditarReceta(@ModelAttribute Receta receta) {
        // Se usa el valor retornado para evitar advertencias
        Receta recetaGuardada = (receta.getId() != null)
                ? recetaService.editarReceta(receta)
                : recetaService.crearReceta(receta);

        // Si algún día quieres redirigir con el ID:
        // return "redirect:/recetas/" + recetaGuardada.getId();

        return "redirect:/recetas";
    }

    @GetMapping("/{id}")
    public String verDetalle(@PathVariable Long id, Model model) {
        Receta receta = recetaService.buscarPorId(id);
        model.addAttribute("receta", receta);
        return "receta-detalle"; // templates/receta-detalle.html
    }

    @PostMapping("/{id}/eliminar")
    public String eliminarReceta(@PathVariable Long id) {
        recetaService.eliminarReceta(id);
        return "redirect:/recetas";
    }
}
