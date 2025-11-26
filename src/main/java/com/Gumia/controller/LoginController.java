package com.Gumia.controller;

import com.Gumia.model.Usuario;
import com.Gumia.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class LoginController {

    private final UsuarioService usuarioService;

    public LoginController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/login")
    public String formularioLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String iniciarSesion(@RequestParam String email,
                                @RequestParam String password,
                                HttpSession session,
                                Model model) {

        Optional<Usuario> usuarioOpt = usuarioService.autenticar(email, password);

        if (usuarioOpt.isPresent()) {
            // Guardamos al usuario en la sesión
            session.setAttribute("usuarioLogueado", usuarioOpt.get());
            return "redirect:/";
        } else {
            model.addAttribute("error", "Credenciales incorrectas");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String cerrarSesion(HttpSession session) {
        session.invalidate(); // Borramos la sesión
        return "redirect:/";
    }
}