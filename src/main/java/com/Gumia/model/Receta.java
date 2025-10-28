package com.Gumia.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
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
    private Usuario usuario;

    @ManyToMany
    @JoinTable(name = "receta_ingrediente", joinColumns = @JoinColumn(name = "receta_id"), inverseJoinColumns = @JoinColumn(name = "ingrediente_id"))
    private List<Ingrediente> ingredientes;

    @OneToMany(mappedBy = "receta")
    private List<Favorito> favoritos;
}
