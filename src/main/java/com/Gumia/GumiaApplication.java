package com.Gumia;

import com.Gumia.model.Ingrediente;
import com.Gumia.model.Receta;
import com.Gumia.model.Usuario;
import com.Gumia.service.RecetaService;
import com.Gumia.service.UsuarioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GumiaApplication implements CommandLineRunner {

    private final UsuarioService usuarioService;
    private final RecetaService recetaService;

    public GumiaApplication(UsuarioService usuarioService, RecetaService recetaService) {
        this.usuarioService = usuarioService;
        this.recetaService = recetaService;
    }

    public static void main(String[] args) {
        SpringApplication.run(GumiaApplication.class, args);
    }

    @Override
    public void run(String... args) {

        // --- USUARIO 1 ---
        Usuario paco = new Usuario("Paco", "paco@gumia.com", "1234");
        usuarioService.registrarUsuario(paco);

        // --- USUARIO 2 ---
        Usuario maria = new Usuario("María", "maria@gumia.com", "1234");
        usuarioService.registrarUsuario(maria);

        // --- USUARIO 3 ---
        Usuario ana = new Usuario("Ana", "ana@gumia.com", "1234");
        usuarioService.registrarUsuario(ana);


        // ===================== RECETAS ===============================

        // 1. Filete de Ternera (Paco)
        Receta ternera = new Receta(
                "Filete de Ternera",
                "Fácil filete con patatas. Perfecto para una cena.",
                30, 1, "Carne y Pescado",
                "/img/recetas/ternera.jpg",
                paco,
                "1. Calentar aceite.\n2. Cocinar el filete.\n3. Preparar patatas.\n4. Servir caliente."
        );
        ternera.addIngrediente(new Ingrediente("Ternera", "200g", ternera));
        ternera.addIngrediente(new Ingrediente("Patatas", "1 unidad", ternera));
        ternera.addIngrediente(new Ingrediente("Aceite", "Al gusto", ternera));
        recetaService.guardarReceta(ternera, paco);


        // 2. Bizcocho de Yogur (María)
        Receta bizcocho = new Receta(
                "Bizcocho de Yogur",
                "Esponjoso y sin gluten.",
                60, 1, "Postres",
                "/img/recetas/bizcocho.jpg",
                maria,
                "1. Mezclar yogur y azúcar.\n2. Añadir huevos.\n3. Agregar harina.\n4. Hornear 40 min a 180ºC."
        );
        bizcocho.addIngrediente(new Ingrediente("Harina de arroz", "3 vasos", bizcocho));
        bizcocho.addIngrediente(new Ingrediente("Yogur", "1", bizcocho));
        bizcocho.addIngrediente(new Ingrediente("Azúcar", "2 vasos", bizcocho));
        recetaService.guardarReceta(bizcocho, maria);



        // 3. Pasta Carbonara (Paco)
        Receta carbonara = new Receta(
                "Pasta Carbonara",
                "Receta italiana auténtica sin nata.",
                20, 2, "Pasta",
                "/img/recetas/pasta.jpg",
                paco,
                "1. Cocer la pasta.\n2. Mezclar yema + queso.\n3. Saltear panceta.\n4. Unir todo fuera del fuego."
        );
        carbonara.addIngrediente(new Ingrediente("Espaguetis", "200g", carbonara));
        carbonara.addIngrediente(new Ingrediente("Panceta", "100g", carbonara));
        carbonara.addIngrediente(new Ingrediente("Yemas", "2", carbonara));
        recetaService.guardarReceta(carbonara, paco);


        // 4. Crema de Calabaza (Ana)
        Receta calabaza = new Receta(
                "Crema de Calabaza",
                "Suave, ligera y saludable.",
                40, 1, "Sopas",
                "/img/recetas/crema.jpg",
                ana,
                "1. Sofreír verduras.\n2. Añadir caldo.\n3. Cocer 20 min.\n4. Triturar."
        );
        calabaza.addIngrediente(new Ingrediente("Calabaza", "500g", calabaza));
        calabaza.addIngrediente(new Ingrediente("Cebolla", "1", calabaza));
        calabaza.addIngrediente(new Ingrediente("Caldo", "500ml", calabaza));
        recetaService.guardarReceta(calabaza, ana);


        // 5. Tortitas Americanas (María)
        Receta tortitas = new Receta(
                "Tortitas Americanas",
                "Perfectas para desayunar.",
                15, 1, "Desayuno",
                "/img/recetas/tortitas.jpg",
                maria,
                "1. Mezclar ingredientes.\n2. Cocinar en sartén.\n3. Servir con sirope."
        );
        tortitas.addIngrediente(new Ingrediente("Harina", "150g", tortitas));
        tortitas.addIngrediente(new Ingrediente("Leche", "200ml", tortitas));
        tortitas.addIngrediente(new Ingrediente("Huevo", "1", tortitas));
        recetaService.guardarReceta(tortitas, maria);


        // 6. Hamburguesa Casera (Paco)
        Receta hamburguesa = new Receta(
                "Hamburguesa Casera",
                "Carne jugosa con pan tostado.",
                25, 2, "Carne y Pescado",
                "/img/recetas/hamburguesa.jpg",
                paco,
                "1. Formar hamburguesa.\n2. Cocinar.\n3. Montar con el pan y toppings."
        );
        hamburguesa.addIngrediente(new Ingrediente("Carne picada", "200g", hamburguesa));
        hamburguesa.addIngrediente(new Ingrediente("Pan brioche", "1", hamburguesa));
        recetaService.guardarReceta(hamburguesa, paco);


        // 7. Guacamole (Ana)
        Receta guacamole = new Receta(
                "Guacamole Casero",
                "Fresco y perfecto como aperitivo.",
                10, 1, "Aperitivos",
                "/img/recetas/guaca.jpg",
                ana,
                "1. Aplastar aguacates.\n2. Añadir cebolla y tomate.\n3. Sal, limón y mezclar."
        );
        guacamole.addIngrediente(new Ingrediente("Aguacate", "2", guacamole));
        guacamole.addIngrediente(new Ingrediente("Limón", "1", guacamole));
        guacamole.addIngrediente(new Ingrediente("Tomate", "1", guacamole));
        recetaService.guardarReceta(guacamole, ana);


        // 8. Pollo al Horno (María)
        Receta pollo = new Receta(
                "Pollo al Horno",
                "Clásico y lleno de sabor.",
                90, 2, "Carne y Pescado",
                "/img/recetas/pollo.jpg",
                maria,
                "1. Preparar el pollo.\n2. Añadir especias.\n3. Hornear 1h 15min."
        );
        pollo.addIngrediente(new Ingrediente("Pollo entero", "1 kg", pollo));
        pollo.addIngrediente(new Ingrediente("Ajo", "2 dientes", pollo));
        recetaService.guardarReceta(pollo, maria);


        // 9. Arroz con Leche (Ana)
        Receta arrozLeche = new Receta(
                "Arroz con Leche",
                "Postre tradicional muy cremoso.",
                45, 1, "Postres",
                "/img/recetas/arroz.jpg",
                ana,
                "1. Hervir leche.\n2. Añadir arroz.\n3. Cocer 30 min.\n4. Añadir azúcar y canela."
        );
        arrozLeche.addIngrediente(new Ingrediente("Leche", "1 L", arrozLeche));
        arrozLeche.addIngrediente(new Ingrediente("Arroz", "150g", arrozLeche));
        arrozLeche.addIngrediente(new Ingrediente("Azúcar", "100g", arrozLeche));
        recetaService.guardarReceta(arrozLeche, ana);


        System.out.println("✅ APLICACIÓN INICIADA CON 10 RECETAS DE PRUEBA");
    }
}
