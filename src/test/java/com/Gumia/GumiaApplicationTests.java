package com.Gumia;

import com.Gumia.model.Usuario;
import com.Gumia.model.Receta;
import com.Gumia.model.Ingrediente;
import com.Gumia.repositories.UsuarioRepository;
import com.Gumia.repositories.RecetaRepository;
import com.Gumia.repositories.IngredienteRepository;
import com.Gumia.service.UsuarioService;
import com.Gumia.service.RecetaService;
import com.Gumia.service.IngredienteService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class GumiaApplicationTests {

    // Unitarios de servicios con Mockito
    @Mock UsuarioRepository usuarioRepository;
    @InjectMocks UsuarioService usuarioService;

    @Mock RecetaRepository recetaRepository;
    @InjectMocks RecetaService recetaService;

    @Mock IngredienteRepository ingredienteRepository;
    @InjectMocks IngredienteService ingredienteService;

    @Test void testGuardarUsuario() {
        Usuario u = new Usuario("Marina", "marina@email.com");
        usuarioService.guardar(u);
        verify(usuarioRepository).save(u);
    }

    @Test void testListarRecetas() {
        when(recetaRepository.findAll()).thenReturn(List.of(new Receta("Pan", "Sin gluten", null)));
        Iterable<Receta> recetas = recetaService.listarTodas();
        assertEquals(1, ((List<Receta>) recetas).size());
    }

    @Test void testGuardarIngrediente() {
        Ingrediente i = new Ingrediente("Harina", "200g", null);
        ingredienteService.guardar(i);
        verify(ingredienteRepository).save(i);
    }

    // Integración con MockMvc
    @Autowired private MockMvc mockMvc;

    @Test void testFormularioUsuario() throws Exception {
        mockMvc.perform(get("/usuarios/nuevo"))
                .andExpect(status().isOk())
                .andExpect(view().name("form_usuario"));
    }

    @Test void testGuardarUsuarioDesdeFormulario() throws Exception {
        mockMvc.perform(post("/usuarios/guardar")
                        .param("nombre", "Marina")
                        .param("email", "marina@email.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/usuarios"));
    }

    @Test void testFormularioReceta() throws Exception {
        mockMvc.perform(get("/recetas/nueva"))
                .andExpect(status().isOk())
                .andExpect(view().name("form_receta"));
    }

    @Test void testFormularioIngrediente() throws Exception {
        mockMvc.perform(get("/ingredientes/nuevo"))
                .andExpect(status().isOk())
                .andExpect(view().name("form_ingrediente"));
    }

    // Validación de entidades
    @Autowired private Validator validator;

    @Test void testUsuarioNombreVacio() {
        Usuario u = new Usuario("", "correo@email.com");
        Set<ConstraintViolation<Usuario>> errores = validator.validate(u);
        assertFalse(errores.isEmpty());
    }

    @Test void testUsuarioEmailInvalido() {
        Usuario u = new Usuario("Marina", "correo-invalido");
        Set<ConstraintViolation<Usuario>> errores = validator.validate(u);
        assertFalse(errores.isEmpty());
    }

    // Test de arranque de la aplicación
    @Test void contextLoads() {
        // Verifica que el contexto de Spring arranca sin errores
    }
}

// Tests de repositorio con H2
@DataJpaTest
class UsuarioRepositoryTest {

    @Autowired private UsuarioRepository usuarioRepository;

    @Test void testGuardarYBuscarUsuario() {
        Usuario u = new Usuario("Marina", "marina@email.com");
        usuarioRepository.save(u);
        Usuario encontrado = usuarioRepository.findById(u.getId()).orElse(null);
        assertNotNull(encontrado);
        assertEquals("Marina", encontrado.getNombre());
    }

    @Test
    void testNoPermiteEmailDuplicado() {
        Usuario u1 = new Usuario("Marina", "marina@email.com");
        usuarioRepository.save(u1); // save() es suficiente si usas @DataJpaTest

        Usuario u2 = new Usuario("Otra", "marina@email.com");

        assertThrows(Exception.class, () -> {
            usuarioRepository.save(u2);
            usuarioRepository.flush(); // fuerza la escritura y validación de restricciones
        });
    }

}

@DataJpaTest
class RecetaRepositoryTest {

    @Autowired private RecetaRepository recetaRepository;

    @Test void testGuardarYBuscarReceta() {
        Receta r = new Receta("Pan", "Sin gluten", null);
        recetaRepository.save(r);
        Receta encontrado = recetaRepository.findById(r.getId()).orElse(null);
        assertNotNull(encontrado);
        assertEquals("Pan", encontrado.getTitulo());
    }
}

@DataJpaTest
class IngredienteRepositoryTest {

    @Autowired private IngredienteRepository ingredienteRepository;

    @Test void testGuardarYBuscarIngrediente() {
        Ingrediente i = new Ingrediente("Harina", "200g", null);
        ingredienteRepository.save(i);
        Ingrediente encontrado = ingredienteRepository.findById(i.getId()).orElse(null);
        assertNotNull(encontrado);
        assertEquals("Harina", encontrado.getNombre());
    }
}
