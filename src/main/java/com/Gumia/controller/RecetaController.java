package com.Gumia.controller;

import com.Gumia.model.Receta;
import com.Gumia.model.Usuario;
import com.Gumia.service.RecetaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class RecetaController {

    private final RecetaService recetaService;

    public RecetaController(RecetaService recetaService) {
        this.recetaService = recetaService;
    }

    // --- VISTAS ---

    @GetMapping("/receta/{id}")
    public String verDetalle(@PathVariable Long id, Model model) {
        Receta receta = recetaService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Receta no encontrada"));
        model.addAttribute("receta", receta);
        return "detalles_receta";
    }

    @GetMapping("/receta/nueva")
    public String formularioNuevaReceta(HttpSession session, Model model) {
        if (session.getAttribute("usuarioLogueado") == null) {
            return "redirect:/login";
        }
        model.addAttribute("receta", new Receta());
        return "form_receta";
    }

    // --- ACCIONES ---

    @PostMapping("/receta/guardar")
    public String guardarReceta(Receta receta, HttpSession session) {
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuarioLogueado == null) {
            return "redirect:/login";
        }

        // LÓGICA DE SEGURIDAD
        if (receta.getId() != null) {
            Receta original = recetaService.buscarPorId(receta.getId()).orElse(null);
            if (original != null) {
                if (!original.getAutor().getId().equals(usuarioLogueado.getId())) {
                    return "redirect:/perfil?error=no_autorizado";
                }
                receta.setAutor(original.getAutor());
            }
        } else {
            receta.setAutor(usuarioLogueado);
        }

        recetaService.guardarReceta(receta, usuarioLogueado);
        return "redirect:/perfil";
    }

    @GetMapping("/receta/borrar/{id}")
    public String borrarReceta(@PathVariable Long id, HttpSession session) {
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuarioLogueado == null) {
            return "redirect:/login";
        }

        Receta receta = recetaService.buscarPorId(id).orElse(null);
        if (receta != null && receta.getAutor().getId().equals(usuarioLogueado.getId())) {
            recetaService.borrarReceta(id);
        }
        return "redirect:/perfil";
    }

    // --- BUSCADOR Y FILTROS ---

    @GetMapping("/buscar")
    public String buscar(@RequestParam String q, Model model) {
        Page<Receta> resultados = recetaService.buscarPorTitulo(q, PageRequest.of(0, 10));

        model.addAttribute("recetas", resultados.getContent());

        if (resultados.isEmpty()) {
            model.addAttribute("mensajeBusqueda", "No se encontraron recetas con: " + q);
        }

        return "index";
    }

    @GetMapping("/recetas/categoria")
    public String listarPorCategoria(@RequestParam String nombre, Model model) {
        List<Receta> filtradas = recetaService.buscarPorCategoria(nombre);
        model.addAttribute("recetas", filtradas);
        return "index";
    }

    @GetMapping("/recetas/dificultad")
    public String listarPorDificultad(@RequestParam int nivel, Model model) {
        List<Receta> filtradas = recetaService.buscarPorDificultad(nivel);
        model.addAttribute("recetas", filtradas);
        return "index";
    }

    // --- MÉTODO NUEVO AÑADIDO PARA EDITAR ---
    @GetMapping("/receta/editar/{id}")
    public String formularioEditarReceta(@PathVariable Long id, Model model, HttpSession session) {
        // 1. Verificación de seguridad (Sesión)
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuarioLogueado == null) {
            return "redirect:/login";
        }

        // 2. Buscar la receta
        Receta receta = recetaService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Receta no encontrada"));

        // 3. Verificación de seguridad (Propiedad)
        // Solo el dueño puede editarla
        if (!receta.getAutor().getId().equals(usuarioLogueado.getId())) {
            return "redirect:/perfil?error=no_autorizado";
        }

        // 4. Pasamos la receta existente al modelo
        // Thymeleaf rellenará los campos (th:field) automáticamente con estos datos
        model.addAttribute("receta", receta);

        // Reutilizamos el mismo formulario de creación
        return "form_receta";
    }
}