package com.Gumia.controller;

import com.Gumia.model.Ingrediente;
import com.Gumia.model.Receta;
import com.Gumia.model.Usuario;
import com.Gumia.repositories.RecetaRepository;
import com.Gumia.repositories.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RecetaRepository recetaRepository;
    private final UsuarioRepository usuarioRepository;

    public DataInitializer(RecetaRepository recetaRepository, UsuarioRepository usuarioRepository) {
        this.recetaRepository = recetaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public void run(String... args) {

        // --- LÓGICA DE PERSISTENCIA ---
        // Comprobamos si la base de datos está vacía.
        // Si count() > 0, significa que ya hay datos (del archivo), así que NO hacemos nada.
        // Esto protege a los usuarios que registres manualmente.

        if (recetaRepository.count() == 0) {

            System.out.println("--- 🚀 BASE DE DATOS VACÍA: CARGANDO DATOS INICIALES... ---");

            // Creamos el Chef por defecto solo la primera vez
            Usuario chef = new Usuario();
            chef.setNombre("Gumia Chef");
            chef.setEmail("chef@gumia.com");
            chef.setPassword("1234");
            usuarioRepository.save(chef);

            // --- 1. FILETES DE TERNERA ---
            crearReceta(chef,
                    "Filetes de ternera con salsa de verduras y seta",
                    "Fácil filete con patatas. Perfecto para una cena.",
                    """
                    1. Cortamos las verduras y las hortalizas según nuestro gusto. Aquí las cortamos bien pequeñitas. Dejamos las setas en su tamaño.
                    
                    2. Vamos a sofreírlas en una sartén con un chorrito de aceite. Primero las verduras crudas y seguido las verduras y setas congeladas que aportan el caldo necesario para que queden blandas. Reservamos.
                    
                    3. Llegados a este punto freímos nuestra ternera. Como siempre a tu gusto. Retiramos y reservamos ya en el plato para servir.
                    
                    4. En la sartén utilizada para la carne vertemos el medio vaso de leche y la nata. Removemos para desgrasar bien la sartén agregamos las verduras reservadas, mezclamos bien, dejamos reducir y probándola, salpimentamos.
                    
                    5. Ya solo queda salpimentar la carne a nuestro gusto y coronarla con un par de buenas cucharadas de la salsa de verduras.
                    """,
                    "/img/ternera.jpg",
                    "Carnes y aves",
                    20, 2,
                    Arrays.asList(
                            ing("Filete de ternera", "2 unds"),
                            ing("Cebolla dulce", "1 und"),
                            ing("Migas de coliflor", "Al gusto"),
                            ing("Calabacín", "1 und"),
                            ing("Mix setas congeladas", "Al gusto"),
                            ing("Nata para cocinar", "Al gusto"),
                            ing("Sal y Pimienta", "Al gusto")
                    )
            );

            // --- 2. BIZCOCHO DE YOGURT ---
            crearReceta(chef,
                    "Bizcocho de yogurt",
                    "Esponjoso y sin gluten.",
                    """
                    1. Mezclar (y si hay grumos, tamizar) el harina junto a la levadura. Reservar.
                    
                    2. En la amasadora, batir enérgicamente los huevos junto con el azúcar a velocidad alta hasta que espumen y dupliquen el volumen.
                    
                    3. Añadir el aceite y seguir batiendo.
                    
                    4. Incorporar el yogur y seguir batiendo.
                    
                    5. Finalmente, bajar la velocidad de la amasadora e ir echando a cucharadas la mezcla de harina, levadura y goma xantana. Hacerlo despacio, dejando que se integren bien.
                    
                    6. Verter la masa en el molde y hornear unos 38/45 minutos, dependiendo del molde que utilices.
                    
                    7. Una vez horneado, dejar en el molde 5 minutos y volcar sobre una rejilla hasta que se enfríe por completo.
                    """,
                    "/img/bizcocho.jpg",
                    "Postres y dulces",
                    50, 1,
                    Arrays.asList(
                            ing("Yogur", "1 vasito"),
                            ing("Aceite", "1 vasito"),
                            ing("Azúcar", "2 vasitos"),
                            ing("Arroz / Almidón maíz", "3 vasitos"),
                            ing("Huevos", "3 unds"),
                            ing("Polvo hornear", "1 cda"),
                            ing("Limón", "1 und"),
                            ing("Azúcar glas", "50g")
                    )
            );

            // --- 3. PASTA CARBONARA ---
            crearReceta(chef,
                    "Pasta carbonara",
                    "Receta italiana auténtica.",
                    """
                    1. Dorar la panceta en aceite.
                    
                    2. En un bol mezclamos los huevos con el queso, la nata, la sal y la pimienta negra molida.
                    
                    3. Cocemos la pasta sin gluten en abundante agua fría.
                    
                    4. Una vez hecha la escurrimos bien y salteamos con la panceta.
                    
                    5. Rehogamos un instante y vertemos en el bol con el resto de la mezcla.
                    
                    6. Mezclamos rápidamente y servimos.
                    """,
                    "/img/pasta.jpg",
                    "Pasta",
                    25, 2,
                    Arrays.asList(
                            ing("Espaguetis sin gluten", "300g"),
                            ing("Panceta fresca/Bacon", "200g"),
                            ing("Huevos", "5 unds"),
                            ing("Parmesano rallado", "80-100g"),
                            ing("Nata líquida", "2 bricks peq"),
                            ing("Pimienta negra", "Al gusto")
                    )
            );

            // --- 4. CREMA DE CALABAZA ---
            crearReceta(chef,
                    "Crema de calabazas y puerro",
                    "Suave, ligera y saludable.",
                    """
                    1. Picar una cebolla y freír con un poco de aceite de oliva a fuego medio en una olla grande.
                    
                    2. Cortar la calabaza en pequeños trozos.
                    
                    3. Lavar los puerros, cortarlos y añadir la calabaza y los puerros a la olla con la cebolla.
                    
                    4. Saltear unos minutos las verduras hasta que se mezcle el conjunto.
                    
                    5. Añadir a continuación el caldo de verduras.
                    
                    6. Dejar que hierva a fuego lento, hasta que las verduras estén blandas, y añadir un poco de sal y pimienta.
                    
                    7. También puede agregarse una pizca de cúrcuma.
                    
                    8. Pasar la crema por la licuadora y después pasar por un colador fino.
                    
                    9. Acabar rectificando de sal y pimienta y añadir un poco de de cebollino o perejil.
                    
                    10. Servir la crema con unas gotas de aceite de semilla de calabaza y espolvorear unas semillas de calabaza tostadas sobre ella.
                    """,
                    "/img/crema.jpg",
                    "Sopas, Cremas y Tortillas",
                    27, 1,
                    Arrays.asList(
                            ing("Calabaza", "300g"),
                            ing("Puerro", "1 und"),
                            ing("Cebolla", "1 und"),
                            ing("Caldo de verduras", "500ml"),
                            ing("Aceite de oliva", "Al gusto"),
                            ing("Semillas calabaza", "Para decorar")
                    )
            );

            // --- 5. TORTITAS AMERICANAS ---
            crearReceta(chef,
                    "Tortitas americanas",
                    "Perfectas para desayunar o merendar.",
                    """
                    1. Poner en un recipiente todos los ingredientes y mezclarlos hasta obtener una masa homogénea.
                    
                    2. Dejarla reposar durante unos 30 minutos.
                    
                    3. Poner a calentar un poco de mantequilla en una sartén antiadherente (de unos 13 cm de diámetro) y añadir un cucharón de mezcla.
                    
                    4. Cocinar la tortita por ambos lados hasta que quede bien dorada y a continuación servirla en un plato.
                    
                    5. Repetir esta operación para preparar los demás y colocarlos uno encima del otro.
                    
                    6. Condimentar las tortitas todavía calientes según el propio gusto: con miel, mermelada, crema de cacao...
                    """,
                    "/img/tortitas.jpg",
                    "Postres y dulces",
                    80, 2,
                    Arrays.asList(
                            ing("Harina Mix C", "250g"),
                            ing("Leche", "250ml"),
                            ing("Huevos", "2 unds"),
                            ing("Sal", "1 pizca"),
                            ing("Azúcar", "2 cdas"),
                            ing("Polvo hornear", "1/2 cdta"),
                            ing("Mantequilla", "Para freír")
                    )
            );

            // --- 6. HAMBURGUESAS CASERAS ---
            crearReceta(chef,
                    "Hamburguesas caseras",
                    "Carne jugosa.",
                    """
                    1. Lo primero sera poner las dos rebanadas de pan en remojo en la leche, para que así se ablande la miga de pan.
                    
                    2. En un recipiente ponemos la carne picada, el huevo, el ajo molido, la pimienta, la sal, el perejil, el ketchup y el pan con la leche.
                    
                    3. Vamos a mezclar muy bien hasta que estén todos los ingredientes completamente integrados.
                    
                    4. Nos mojamos las palmas de las manos y empezamos a formar nuestras hamburguesas caseras.
                    
                    5. Cogemos una bola y la aplastamos dándole forma.
                    
                    6. La colocamos en una papel de horno.
                    
                    7. Una vez la tengamos todas cortamos el papel de horno para que estén individuales cada hamburguesa casera.
                    """,
                    "/img/hamburguesa.jpg",
                    "Carnes y aves",
                    40, 2,
                    Arrays.asList(
                            ing("Carne picada", "600g"),
                            ing("Leche entera", "100ml"),
                            ing("Huevo", "1 und"),
                            ing("Pan", "2 rebanadas"),
                            ing("Ajo molido", "1/2 cdta"),
                            ing("Perejil fresco", "1 cda"),
                            ing("Ketchup", "30g")
                    )
            );

            // --- 7. GUACAMOLE ---
            crearReceta(chef,
                    "Guacamole",
                    "Aperitivo fresco.",
                    """
                    1. Pelamos los aguacates y los ponemos en un bol. Picamos la cebolleta y el tomate.
                    
                    2. Aplastamos el aguacate con un tenedor. Añadimos los tomates, la cebolleta y el zumo de lima. Rectificamos de sal. Yo le pongo una especia que tengo que le da un toque picante, pero le podéis poner jalapeños.
                    """,
                    "/img/guaca.jpg",
                    "Aperitivos",
                    30, 1,
                    Arrays.asList(
                            ing("Aguacates", "2 unds"),
                            ing("Tomate", "1 und"),
                            ing("Cebolleta", "1 und"),
                            ing("Zumo lima", "1 und"),
                            ing("Jalapeños/Chile", "Opcional"),
                            ing("Cilantro", "Opcional")
                    )
            );

            // --- 8. POLLO AL HORNO ---
            crearReceta(chef,
                    "Pollo al horno con patatas",
                    "Clásico asado.",
                    """
                    1. Comenzamos limpiando el pollo. Este proceso lo debe de hacer el carnicero.
                    
                    2. Una vez limpio vamos a preparar una marinada para asar un pollo. En un mortero machacamos un diente de ajo con una cucharada de granos de pimienta negra. Cuando lo tengamos listo, añadimos perejil y romero (tomillo le va genial) y seguidamente la manteca de cerdo hasta crear una marinada para el pollo muy aromática.
                    
                    3. Untamos con la marina de hierbas todo el pollo, por dentro y por fuera. Cubrimos de papel film y dejamos poco a poco que vaya cogiendo sabor.
                    
                    4. Al día siguiente hacemos una cama de patatas cortadas para panadera y la cebolla en juliana en una bandeja de horno. Ponemos sal, unas hierbas aromáticas, aceite de oliva y reservamos.
                    
                    5. Introducimos dentro del pollo un limón entero cortado a la mitad y unas ramas de tomillo y romero. Lo cerramos con hilo de horno.
                    
                    6. Colocamos el pollo entero sobre las pechugas encima de las patatas y precalentamos el horno a 180º.
                    
                    7. El tiempo de horneado para asar un pollo es de 1 hora y 40 minutos. Vamos a introducir al horno y vamos a ir regando cada 15 minutos con una mezcla que hemos hecho de vino blanco y caldo de pollo a partes iguales.
                    
                    8. A los 45 minutos sacamos el pollo del horno y le damos la vuelta. Lo volvemos a meter a hornear ahora con las pechugas para arriba hasta que finalice el tiempo.
                    """,
                    "/img/pollo.jpg",
                    "Carnes y aves",
                    130, 3,
                    Arrays.asList(
                            ing("Pollo para asar", "1 und"),
                            ing("Patatas", "4 unds"),
                            ing("Cebolla", "1 und"),
                            ing("Manteca cerdo", "1 cda"),
                            ing("Limón", "1 und"),
                            ing("Vino blanco", "200ml"),
                            ing("Caldo de pollo", "200ml")
                    )
            );

            // --- 9. ARROZ CON LECHE ---
            crearReceta(chef,
                    "Arroz con leche",
                    "Postre tradicional.",
                    """
                    1. Ponemos el arroz con un pellizco de sal, la canela y la cascara de limón en una olla y cubrimos de agua.
                    
                    2. Lo dejamos hervir sobre 10 minutos sin dejar de remover.
                    
                    3. En una cazuela aparte ponemos a calentar la leche fresca Central Lechera Asturiana. Importante: No dejes que la leche hierva, eso estropearía su sabor.
                    
                    4. Poco a poco vamos a ir añadiendo la leche caliente a la olla del arroz.
                    
                    5. Sin que deje de hervir nuestra olla con el arroz, vamos añadiendo la leche a cacitos removiendo constantemente durante más o menos media hora.
                    
                    6. Cuando el arroz esté cocinado, añadimos el azúcar poco a poco y vamos dejando que se disuelva.
                    
                    7. Dejamos enfriar en la nevera y servimos con un poco de canela en polvo por encima.
                    """,
                    "/img/arroz.jpg",
                    "Postres y dulces",
                    50, 2,
                    Arrays.asList(
                            ing("Leche fresca", "1 L"),
                            ing("Arroz redondo", "120g"),
                            ing("Palo canela", "1 und"),
                            ing("Piel limón", "1 und"),
                            ing("Azúcar", "5 cdas"),
                            ing("Canela polvo", "Al gusto")
                    )
            );

            System.out.println("--- ✅ DATOS CARGADOS CORRECTAMENTE ---");

        } else {
            // Este mensaje saldrá si ya existen datos (cuando reinicies la app)
            System.out.println("--- 👌 LA BASE DE DATOS YA TIENE DATOS. SE MANTIENEN LOS USUARIOS. ---");
        }
    }

    // --- MÉTODOS AUXILIARES ---

    private Ingrediente ing(String nombre, String cantidad) {
        return new Ingrediente(nombre, cantidad, null);
    }

    private void crearReceta(Usuario autor, String titulo, String descripcion, String preparacion,
                             String urlImagen, String categoria, int tiempo, int dificultad,
                             List<Ingrediente> ingredientes) {

        Receta receta = new Receta(titulo, descripcion, tiempo, dificultad, categoria, urlImagen, autor, preparacion);

        for (Ingrediente i : ingredientes) {
            i.setReceta(receta);
        }
        receta.setIngredientes(ingredientes);

        recetaRepository.save(receta);
    }
}