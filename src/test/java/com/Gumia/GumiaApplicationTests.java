package com.Gumia;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GumiaApplicationTests {

    @BeforeEach
    void setUp() {
        // Insertar 100 usuarios usando batch para evitar múltiples sentencias en un solo string
        List<Object[]> usuarios = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            usuarios.add(new Object[]{ "usuario" + i });
        }
        jdbcTemplate.batchUpdate("INSERT INTO usuario (nombre) VALUES (?)", usuarios);

        // Insertar 10 recetas
        List<Object[]> recetas = Arrays.asList(
                new Object[]{ "receta1", "Descripción de la receta 1", 30, 2, "http://example.com/receta1", 1 },
                new Object[]{ "receta2", "Descripción de la receta 2", 30, 2, "http://example.com/receta2", 1 },
                new Object[]{ "receta3", "Descripción de la receta 3", 30, 2, "http://example.com/receta3", 1 },
                new Object[]{ "receta4", "Descripción de la receta 4", 30, 2, "http://example.com/receta4", 1 },
                new Object[]{ "receta5", "Descripción de la receta 5", 30, 2, "http://example.com/receta5", 1 },
                new Object[]{ "macarrones", "Descripción de la receta 6", 30, 2, "http://example.com/receta6", 1 },
                new Object[]{ "receta7", "Descripción de la receta 7", 30, 2, "http://example.com/receta7", 1 },
                new Object[]{ "receta8", "Descripción de la receta 8", 30, 2, "http://example.com/receta8", 1 },
                new Object[]{ "receta9", "Descripción de la receta 9", 30, 2, "http://example.com/receta9", 1 },
                new Object[]{ "receta10", "Descripción de la receta 10", 30, 2, "http://example.com/receta10", 1 }
        );
        jdbcTemplate.batchUpdate(
                "INSERT INTO receta (titulo, descripcion, tiempo, dificultad, url, usuario_id) VALUES (?, ?, ?, ?, ?, ?)",
                recetas
        );

        // Insertar ingredientes
        List<Object[]> ingredientes = Arrays.asList(
                new Object[]{ "patata", 4, 2 },
                new Object[]{ "guisante", 5, 3 },
                new Object[]{ "pepino", 2, 4 },
                new Object[]{ "espagetti", 1, 4 },
                new Object[]{ "pasta", 3, 4 },
                new Object[]{ "ingrediente6", 3, 1 },
                new Object[]{ "ingrediente7", 3, 1 },
                new Object[]{ "ingrediente8", 3, 1 },
                new Object[]{ "ingrediente9", 3, 4 },
                new Object[]{ "ingrediente10", 3, 4 }
        );
        jdbcTemplate.batchUpdate("INSERT INTO ingrediente (nombre, cantidad, receta_id) VALUES (?, ?, ?)", ingredientes);

        // Insertar relaciones receta_ingrediente
        List<Object[]> recetaIngrediente = Arrays.asList(
                new Object[]{ 1, 4, 1 },
                new Object[]{ 1, 4, 2 },
                new Object[]{ 1, 4, 3 },
                new Object[]{ 1, 4, 4 },
                new Object[]{ 1, 4, 5 }
        );
        jdbcTemplate.batchUpdate(
                "INSERT INTO receta_ingrediente (receta_id, ingrediente_cantidad, ingrediente_id) VALUES (?, ?, ?)",
                recetaIngrediente
        );

        // Insertar favoritos
        List<Object[]> favoritos = Arrays.asList(
                new Object[]{ 1, 1 },
                new Object[]{ 3, 1 },
                new Object[]{ 5, 1 },
                new Object[]{ 2, 2 },
                new Object[]{ 4, 2 }
        );
        jdbcTemplate.batchUpdate("INSERT INTO favoritos (receta_id, usuario_id) VALUES (?, ?)", favoritos);
    }

    @Test
    void alMenosUno() {
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM usuario", Integer.class);
        assertTrue(count >=1, "Debe haber al menos 1 usuarios (setup)");
    }

    @Test
    void alMenosUnIngrediente() {
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM ingrediente", Integer.class);
        assertTrue(count >=1, "Debe haber al menos 1 ingrediente (setup)");
    }

    @Test
    void alMenosUnaReceta() {
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM receta", Integer.class);
        assertTrue(count >=1, "Debe haber al menos 1 receta (setup)");
    }

    @Test
    void hayMacarrones() {
        String titulo = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM receta WHERE titulo = ?",
                String.class, "macarrones"
        );
        assertNotNull(titulo);
    }

    @Test
    void hayUsuario1() {
        String titulo = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM usuario WHERE nombre = ?",
                String.class, "usuario1"
        );
        assertNotNull(titulo);
    }

    @Test
    void hayPatata() {
        String titulo = jdbcTemplate.queryForObject(
                "SELECT r.titulo FROM receta r JOIN ingrediente i ON i.receta_id = r.id WHERE i.nombre = ? LIMIT 1",
                String.class, "patata"
        );
        assertNotNull(titulo);
    }

    @Test
    void receta1Cantidades4() {
        Integer distintos = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM receta_ingrediente WHERE receta_id = ? AND ingrediente_cantidad <> ?",
                Integer.class, 1, 4
        );
        assertNotNull(distintos);
        assertEquals(0, distintos.intValue(), "Todas las relaciones de receta_ingrediente para receta 1 deben tener ingrediente_cantidad = 4");
    }


        @Autowired
        private JdbcTemplate jdbcTemplate;
}
