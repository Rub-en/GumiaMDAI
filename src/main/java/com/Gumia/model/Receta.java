package com.Gumia.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Receta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descripcion;

    @ManyToOne
    private Usuario autor;

    @OneToMany(mappedBy = "receta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ingrediente> ingredientes = new ArrayList<>();

    // Empty constructor
    public Receta() {
    }

    // Full constructor
    public Receta(Long id, String titulo, String descripcion, Usuario autor, List<Ingrediente> ingredientes) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.autor = autor;
        this.ingredientes = ingredientes;
    }

    // Constructor without id
    public Receta(String titulo, String descripcion, Usuario autor, List<Ingrediente> ingredientes) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.autor = autor;
        this.ingredientes = ingredientes;
    }

    // Constructor without ingredient list
    public Receta(String titulo, String descripcion, Usuario autor) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.autor = autor;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    public List<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }

    // Helper methods
    public void addIngrediente(Ingrediente ingrediente) {
        ingredientes.add(ingrediente);
        ingrediente.setReceta(this);
    }

    public void removeIngrediente(Ingrediente ingrediente) {
        ingredientes.remove(ingrediente);
        ingrediente.setReceta(null);
    }
}
