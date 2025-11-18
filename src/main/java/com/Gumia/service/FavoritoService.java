package com.Gumia.service;

import com.Gumia.model.Usuario;
import com.Gumia.model.Receta;
import com.Gumia.repositories.UsuarioRepository;
import com.Gumia.repositories.RecetaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoritoService {

    private final UsuarioRepository usuarioRepository;
    private final RecetaRepository recetaRepository;

    public FavoritoService(UsuarioRepository usuarioRepository, RecetaRepository recetaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.recetaRepository = recetaRepository;
    }

    // Marcar receta como favorita
    public void marcarFavorito(Long usuarioId, Long recetaId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Receta receta = recetaRepository.findById(recetaId)
                .orElseThrow(() -> new RuntimeException("Receta no encontrada"));

        usuario.getRecetasFavoritas().add(receta);
        usuarioRepository.save(usuario);
    }

    // Eliminar receta de favoritos
    public void eliminarFavorito(Long usuarioId, Long recetaId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Receta receta = recetaRepository.findById(recetaId)
                .orElseThrow(() -> new RuntimeException("Receta no encontrada"));

        usuario.getRecetasFavoritas().remove(receta);
        usuarioRepository.save(usuario);
    }

    // Listar favoritos de un usuario
    public List<Receta> listarFavoritos(Long usuarioId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return usuario.getRecetasFavoritas();
    }
}
