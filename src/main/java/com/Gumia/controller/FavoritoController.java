package com.Gumia.controller;

import com.Gumia.model.Usuario;
import com.Gumia.model.Receta;
import com.Gumia.service.FavoritoService;
import com.Gumia.service.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/favoritos")
public class FavoritoController {

    private final FavoritoService favoritoService;
    private final UsuarioService usuarioService;

    public FavoritoController(FavoritoService favoritoService, UsuarioService usuarioService) {
        this.favoritoService = favoritoService;
        this.usuarioService = usuarioService;
    }

    // Mostrar lista de favoritos
    @GetMapping("/{usuarioId}")
    public String listarFavoritos(@PathVariable Long usuarioId, Model model) {
        List<Receta> favoritos = favoritoService.listarFavoritos(usuarioId);
        Usuario usuario = usuarioService.buscarPorId(usuarioId);
        model.addAttribute("usuario", usuario);
        model.addAttribute("favoritos", favoritos);
        return "favoritos"; // Renderiza favoritos.html
    }

    // Marcar receta como favorita
    @PostMapping("/{usuarioId}/{recetaId}/add")
    public String marcarFavorito(@PathVariable Long usuarioId, @PathVariable Long recetaId) {
        favoritoService.marcarFavorito(usuarioId, recetaId);
        return "redirect:/favoritos/" + usuarioId;
    }

    // Eliminar receta de favoritos
    @PostMapping("/{usuarioId}/{recetaId}/remove")
    public String eliminarFavorito(@PathVariable Long usuarioId, @PathVariable Long recetaId) {
        favoritoService.eliminarFavorito(usuarioId, recetaId);
        return "redirect:/favoritos/" + usuarioId;
    }
}
