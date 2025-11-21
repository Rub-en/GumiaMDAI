package com.Gumia.controller;

import com.Gumia.model.Usuario;
import com.Gumia.repositories.UsuarioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegistrarController {

    private final UsuarioRepository usuarioRepository;
    //private final PasswordEncoder passwordEncoder;

    public RegistrarController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
        //this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute Usuario usuario, @RequestParam String confirmPassword, Model model) {
        if (!usuario.getPassword().equals(confirmPassword)) {
            model.addAttribute("error", "Las contraseñas no coinciden");
            return "register";
        }
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()
                || usuarioRepository.findByUsername(usuario.getNombre()).isPresent()) {
            model.addAttribute("error", "Usuario o correo ya registrado");
            return "register";
        }
        //usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioRepository.save(usuario);
        return "redirect:/login";
    }
}
