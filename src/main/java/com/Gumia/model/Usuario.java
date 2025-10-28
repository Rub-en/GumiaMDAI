package com.Gumia.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @OneToMany(mappedBy = "usuario")
    private List<Receta> recetas;

    @OneToMany(mappedBy = "usuario")
    private List<Favorito> favoritos;
}
