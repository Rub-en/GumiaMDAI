package com.Gumia.service;

import com.Gumia.model.Receta;
import com.Gumia.model.Usuario;
import com.Gumia.repositories.RecetaRepository;
import com.Gumia.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RecetaRepository recetaRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, RecetaRepository recetaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.recetaRepository = recetaRepository;
    }

    // 1. Registro de usuarios
    // CAMBIO: Ahora es 'void' para evitar el warning "Return value is never used"
    public void registrarUsuario(Usuario usuario) {
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya está en uso");
        }
        usuarioRepository.save(usuario);
    }

    // 2. Login simple
    public Optional<Usuario> autenticar(String email, String password) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        if (usuarioOpt.isPresent() && usuarioOpt.get().getPassword().equals(password)) {
            return usuarioOpt;
        }
        return Optional.empty();
    }

    // 3. Gestión de Perfil
    // Este lo mantenemos devolviendo Usuario por si acaso lo usas en el futuro para mostrar datos actualizados
    public Usuario editarPerfil(Usuario usuarioActualizado) {
        return usuarioRepository.save(usuarioActualizado);
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    // 4. Gestión de Favoritos (Añadir)
    @Transactional
    public void agregarFavorito(Long usuarioId, Long recetaId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Receta receta = recetaRepository.findById(recetaId)
                .orElseThrow(() -> new RuntimeException("Receta no encontrada"));

        if (!usuario.getFavoritos().contains(receta)) {
            usuario.getFavoritos().add(receta);
            usuarioRepository.save(usuario);
        }
    }

    // 5. Gestión de Favoritos (Eliminar)
    @Transactional
    public void eliminarFavorito(Long usuarioId, Long recetaId) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Receta receta = recetaRepository.findById(recetaId)
                .orElseThrow(() -> new RuntimeException("Receta no encontrada"));

        if (usuario.getFavoritos().contains(receta)) {
            usuario.getFavoritos().remove(receta);
            usuarioRepository.save(usuario);
        }
    }
}