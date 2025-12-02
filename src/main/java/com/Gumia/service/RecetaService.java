package com.Gumia.service;

import com.Gumia.model.Receta;
import com.Gumia.model.Usuario;
import com.Gumia.repositories.RecetaRepository;
import com.Gumia.repositories.UsuarioRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RecetaService {

    private final RecetaRepository recetaRepository;
    private final UsuarioRepository usuarioRepository;

    public RecetaService(RecetaRepository recetaRepository, UsuarioRepository usuarioRepository) {
        this.recetaRepository = recetaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // 1. Crear y Editar Receta
    @Transactional
    public void guardarReceta(Receta receta, Usuario autor) {
        // Si es nueva (id null), asignamos el autor.
        // Si es edición, el autor ya viene seteado o se mantiene el original.
        if (receta.getId() == null) {
            receta.setAutor(autor);
        }
        recetaRepository.save(receta);
    }

    // 2. Borrar Receta (CORREGIDO PARA EVITAR ERROR 500)
    @Transactional
    public void borrarReceta(Long id) {
        Receta receta = recetaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Receta no encontrada"));

        // Desvincular de favoritos antes de borrar
        for (Usuario usuario : receta.getUsuariosQueLaTienenEnFavoritos()) {
            usuario.getFavoritos().remove(receta);
            usuarioRepository.save(usuario);
        }

        recetaRepository.delete(receta);
    }

    // 3. Consultas básicas
    public List<Receta> listarTodas() {
        return recetaRepository.findAll();
    }

    public Optional<Receta> buscarPorId(Long id) {
        return recetaRepository.findById(id);
    }

    // 4. Filtros
    public List<Receta> buscarPorCategoria(String categoria) {
        return recetaRepository.findByCategoria(categoria);
    }

    public List<Receta> buscarPorDificultad(int dificultad) {
        return recetaRepository.findByDificultad(dificultad);
    }

    // 5. Buscador Avanzado (Por título O ingrediente)
    // CORREGIDO: El parámetro se llama 'texto' para coincidir con la llamada interna
    public Page<Receta> buscarPorTitulo(String texto, Pageable pageable) {
        return recetaRepository.findDistinctByTituloContainingIgnoreCaseOrIngredientes_NombreContainingIgnoreCase(texto, texto, pageable);
    }
}