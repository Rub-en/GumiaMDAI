package com.Gumia.controller;

import com.Gumia.model.Usuario;
import com.Gumia.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrarController {

    private final UsuarioService usuarioService;

    public RegistrarController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/registrar")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }

    @PostMapping("/registrar")
    public String registrarUsuario(@ModelAttribute("usuario") Usuario usuario, Model model) {
        try {
            usuarioService.registrarUsuario(usuario);
            return "redirect:/login"; // Éxito -> Login
        } catch (Exception e) {
            // Error (email duplicado) -> Volver al formulario con mensaje
            model.addAttribute("error", "Error: " + e.getMessage());
            return "registro";
        }
    }
}