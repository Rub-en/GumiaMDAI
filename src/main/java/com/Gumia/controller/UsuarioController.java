package com.Gumia.controller;

import com.Gumia.model.Usuario;
import com.Gumia.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/perfil")
    public String miPerfil(HttpSession session, Model model) {
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");

        if (usuarioLogueado == null) {
            return "redirect:/login";
        }

        // Recargamos el usuario de la BD para tener los datos frescos (favoritos, recetas...)
        Usuario usuarioActualizado = usuarioService.buscarPorId(usuarioLogueado.getId()).orElse(usuarioLogueado);

        model.addAttribute("usuario", usuarioActualizado);
        return "perfil";
    }

    // Añadir a Favoritos
    @GetMapping("/favoritos/add/{idReceta}")
    public String agregarFavorito(@PathVariable Long idReceta, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario != null) {
            usuarioService.agregarFavorito(usuario.getId(), idReceta);
        }
        return "redirect:/receta/" + idReceta;
    }

    // Quitar de Favoritos
    @GetMapping("/favoritos/remove/{idReceta}")
    public String eliminarFavorito(@PathVariable Long idReceta, HttpSession session) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuario != null) {
            usuarioService.eliminarFavorito(usuario.getId(), idReceta);
        }
        return "redirect:/perfil";
    }
}