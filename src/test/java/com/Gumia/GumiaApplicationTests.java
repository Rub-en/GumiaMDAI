package com.Gumia;

import com.Gumia.model.Usuario;
import com.Gumia.model.Receta;
import com.Gumia.model.Ingrediente;
import com.Gumia.repositories.UsuarioRepository;
import com.Gumia.repositories.RecetaRepository;
import com.Gumia.repositories.IngredienteRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GumiaApplicationTests {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RecetaRepository recetaRepository;

    @Autowired
    private IngredienteRepository ingredienteRepository;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        // Crear un usuario inicial para usar en los tests
        usuario = new Usuario();
        usuario.setNombre("Marina");
        usuario.setEmail("marina@example.com");
        usuario.setContrasenia("1234");
        usuario = usuarioRepository.save(usuario);
    }

    // --- TESTS DE USUARIO ---
    @Test
    void crearUsuario() {
        // Comprobar que el usuario se ha guardado correctamente en la base de datos
        assertTrue(usuarioRepository.existsByEmail("marina@example.com"));
    }

    @Test
    void evitarDuplicadosPorEmail() {
        // Intentar guardar otro usuario con el mismo email debe lanzar una excepción
        Usuario duplicado = new Usuario();
        duplicado.setNombre("Otra Marina");
        duplicado.setEmail("marina@example.com");
        duplicado.setContrasenia("abcd");
        assertThrows(Exception.class, () -> usuarioRepository.saveAndFlush(duplicado));
    }

    @Test
    void editarPerfilUsuario() {
        // Actualizar el nombre del usuario y comprobar que se guarda correctamente
        usuario.setNombre("Marina Actualizada");
        usuarioRepository.save(usuario);
        Usuario actualizado = usuarioRepository.findByEmail("marina@example.com");
        assertEquals("Marina Actualizada", actualizado.getNombre());
    }

    @Test
    void eliminarUsuario() {
        // Eliminar el usuario y comprobar que ya no existe en la base de datos
        usuarioRepository.delete(usuario);
        assertFalse(usuarioRepository.existsByEmail("marina@example.com"));
    }

    // --- TESTS DE RECETA ---
    @Test
    void crearRecetaConIngredientes() {
        // Crear una receta con dos ingredientes y comprobar que se guardan correctamente
        Receta receta = new Receta();
        receta.setTitulo("Tarta sin gluten");
        receta.setDescripcion("Deliciosa tarta");
        receta.setTiempoPreparacion(60);
        receta.setDificultad("Media");
        receta.setCategoria("Postre");
        receta.setAutor(usuario);

        Ingrediente ing1 = new Ingrediente();
        ing1.setNombre("Harina de arroz");
        ing1.setCantidad("200g");
        ing1.setReceta(receta);

        Ingrediente ing2 = new Ingrediente();
        ing2.setNombre("Huevos");
        ing2.setCantidad("3");
        ing2.setReceta(receta);

        receta.setIngredientes(List.of(ing1, ing2));
        recetaRepository.save(receta);

        Receta guardada = recetaRepository.findByTitulo("Tarta sin gluten").getFirst();
        assertEquals(2, guardada.getIngredientes().size());
    }

    @Test
    void buscarRecetaPorCategoria() {
        // Guardar una receta en la categoría "Pasta" y comprobar que se puede buscar por categoría
        Receta receta = new Receta();
        receta.setTitulo("Pizza sin gluten");
        receta.setCategoria("Pasta");
        receta.setAutor(usuario);
        recetaRepository.save(receta);

        List<Receta> recetas = recetaRepository.findByCategoria("Pasta");
        assertFalse(recetas.isEmpty());
    }

    @Test
    void buscarRecetaPorTituloParcial() {
        // Guardar una receta y comprobar que se puede buscar por parte del título ignorando mayúsculas
        Receta receta = new Receta();
        receta.setTitulo("Bizcocho sin gluten");
        receta.setAutor(usuario);
        recetaRepository.save(receta);

        List<Receta> recetas = recetaRepository.findByTituloContainingIgnoreCase("bizcocho");
        assertEquals(1, recetas.size());
    }

    // --- TESTS DE FAVORITOS ---
    @Test
    void marcarRecetaComoFavorita() {
        // Comprobar que un usuario puede marcar una receta como favorita
        Receta receta = new Receta();
        receta.setTitulo("Macarrones sin gluten");
        receta.setAutor(usuario);
        recetaRepository.save(receta);

        usuario.getRecetasFavoritas().add(receta);
        usuarioRepository.save(usuario);

        Usuario u = usuarioRepository.findByEmail("marina@example.com");
        assertEquals(1, u.getRecetasFavoritas().size());
    }

    @Test
    void recetaFavoritaDeVariosUsuarios() {
        // Comprobar que una receta puede ser favorita de varios usuarios
        Receta receta = new Receta();
        receta.setTitulo("Paella sin gluten");
        receta.setAutor(usuario);
        recetaRepository.save(receta);

        Usuario otro = new Usuario();
        otro.setNombre("Pedro");
        otro.setEmail("pedro@example.com");
        otro.setContrasenia("1234");
        otro.getRecetasFavoritas().add(receta);
        usuarioRepository.save(otro);

        Receta r = recetaRepository.findByTitulo("Paella sin gluten").getFirst();
        assertEquals(2, r.getUsuariosFavorito().size());
    }

    // --- TEST FUNCIONAL COMPLETO ---
    @Test
    void flujoCompletoUsuarioRecetaFavoritos() {
        // Flujo completo: usuario crea receta, añade ingrediente, la marca como favorita y luego la elimina
        Receta receta = new Receta();
        receta.setTitulo("Brownie sin gluten");
        receta.setAutor(usuario);

        Ingrediente ing = new Ingrediente();
        ing.setNombre("Chocolate");
        ing.setCantidad("100g");
        ing.setReceta(receta);

        receta.setIngredientes(List.of(ing));
        recetaRepository.save(receta);

        usuario.getRecetasFavoritas().add(receta);
        usuarioRepository.save(usuario);

        Usuario u = usuarioRepository.findByEmail("marina@example.com");
        assertEquals("Brownie sin gluten", u.getRecetasFavoritas().getFirst().getTitulo());

        recetaRepository.delete(receta);
        assertTrue(recetaRepository.findByTitulo("Brownie sin gluten").isEmpty());
    }
}
