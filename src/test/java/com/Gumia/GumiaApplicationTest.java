package com.Gumia;

import com.Gumia.model.Ingrediente;
import com.Gumia.model.Receta;
import com.Gumia.model.Usuario;
import com.Gumia.repositories.UsuarioRepository;
import com.Gumia.service.RecetaService;
import com.Gumia.service.UsuarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GumiaApplicationTests {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RecetaService recetaService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Eliminado recetaRepository que no se usaba y daba warning

    @Test
    @DisplayName("Sanity Check: El contexto carga correctamente")
    void contextLoads() {
        assertNotNull(usuarioService);
        assertNotNull(recetaService);
    }

    @Test
    @Transactional
    @DisplayName("Test de Registro: Éxito y Fallo por duplicado")
    void registroUsuarioTest() {
        Usuario u1 = new Usuario("TestUser", "test@gumia.com", "1234");
        usuarioService.registrarUsuario(u1);

        assertNotNull(u1.getId(), "El ID no debería ser nulo tras registrarse");
        assertTrue(usuarioRepository.findByEmail("test@gumia.com").isPresent());

        Usuario u2 = new Usuario("Hacker", "test@gumia.com", "0000");

        Exception exception = assertThrows(RuntimeException.class, () ->
                usuarioService.registrarUsuario(u2)
        );

        String mensajeEsperado = "El email ya está en uso";
        assertTrue(exception.getMessage().contains(mensajeEsperado));
    }

    @Test
    @Transactional
    @DisplayName("Test Recetas: Crear, Ingredientes y Buscador")
    void flujoRecetasTest() {
        Usuario autor = new Usuario("Chef", "chef@test.com", "123");
        usuarioService.registrarUsuario(autor);

        Receta receta = new Receta("Tarta de Manzana", "Deliciosa", 60, 2, "Postres", "url", autor);
        receta.addIngrediente(new Ingrediente("Manzanas", "4", receta));
        receta.addIngrediente(new Ingrediente("Harina", "200g", receta));

        recetaService.guardarReceta(receta, autor);

        Page<Receta> resultados = recetaService.buscarPorTitulo("Manzana", PageRequest.of(0, 10));

        assertEquals(1, resultados.getTotalElements());

        // CORREGIDO: Usamos .getFirst() en lugar de .get(0) para Java 21
        Receta encontrada = resultados.getContent().getFirst();

        assertEquals("Tarta de Manzana", encontrada.getTitulo());
        assertEquals(2, encontrada.getIngredientes().size());
    }

    @Test
    @Transactional
    @DisplayName("Test Favoritos: Añadir y Quitar")
    void favoritosTest() {
        Usuario creador = new Usuario("Creador", "c@gumia.com", "1");
        Usuario fan = new Usuario("Fan", "f@gumia.com", "1");
        usuarioService.registrarUsuario(creador);
        usuarioService.registrarUsuario(fan);

        Receta receta = new Receta("Pizza", "Rica", 20, 1, "Cena", "url", creador);
        recetaService.guardarReceta(receta, creador);

        usuarioService.agregarFavorito(fan.getId(), receta.getId());

        Usuario fanActualizado = usuarioRepository.findById(fan.getId()).orElseThrow();
        assertEquals(1, fanActualizado.getFavoritos().size());

        // CORREGIDO: Usamos .getFirst() en lugar de .get(0) para Java 21
        assertEquals("Pizza", fanActualizado.getFavoritos().getFirst().getTitulo());

        usuarioService.eliminarFavorito(fan.getId(), receta.getId());

        fanActualizado = usuarioRepository.findById(fan.getId()).orElseThrow();
        assertTrue(fanActualizado.getFavoritos().isEmpty());
    }
}