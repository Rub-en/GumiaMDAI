package com.Gumia.service;

import com.Gumia.model.Usuario;
import com.Gumia.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Iterable<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public void guardar(Usuario usuario) {
        usuarioRepository.save(usuario);
    }
}
