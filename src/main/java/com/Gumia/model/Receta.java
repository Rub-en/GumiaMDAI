package com.gumia.model;

import jakarta.persistence.*;
import java.util.List;
import com.gumia.model.Usuario;
import com.gumia.model.Ingrediente;
import com.gumia.model.Favorito;

@Entity
@Table(name = "receta")
public class Receta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descripcion;
    private int tiempo;
    private int dificultad;
    private String url;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToMany
    @JoinTable(
            name = "receta_ingrediente",
            joinColumns = @JoinColumn(name = "receta_id"),
            inverseJoinColumns = @JoinColumn(name = "ingrediente_id")
    )
    private List<Ingrediente> ingredientes;

    @OneToMany(mappedBy = "receta")
    private List<Favorito> favoritos;

    // Getters and Setters...
    // (ya los tienes bien definidos)
}
