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
    private String url;
    private int dificultad;
    private int tiempo;

    @ManyToOne
    private Usuario autor;

    @ManyToMany
    @JoinTable(
            name = "receta_ingrediente",
            joinColumns = @JoinColumn(name = "receta_id"),
            inverseJoinColumns = {
                    @JoinColumn(name = "ingrediente_id", referencedColumnName = "id"),
                    @JoinColumn(name = "ingrediente_cantidad", referencedColumnName = "cantidad")
            }
    )
    private List<Ingrediente> ingredientes;

    @ManyToMany
    @JoinTable(
            name = "favoritos",
            joinColumns = @JoinColumn(name = "receta_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private List<Usuario> favoritos;

    // Empty constructor
    public Receta() {
    }

    // Full constructor
    public Receta(Long id, String titulo, String descripcion, Usuario autor, List<Ingrediente> ingredientes, String url, int dificultad, int tiempo) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.autor = autor;
        this.ingredientes = ingredientes;
        this.url=url;
        this.dificultad=dificultad;
        this.tiempo=tiempo;
    }

    // Constructor without id
    public Receta(String titulo, String descripcion, Usuario autor, List<Ingrediente> ingredientes, String url, int dificultad, int tiempo) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.autor = autor;
        this.ingredientes = ingredientes;
        this.url=url;
        this.dificultad=dificultad;
        this.tiempo=tiempo;
    }

    // Constructor without ingredient list
    public Receta(String titulo, String descripcion, Usuario autor, String url, int dificultad, int tiempo) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.autor = autor;
        this.url=url;
        this.dificultad=dificultad;
        this.tiempo=tiempo;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getDificultad() {
        return dificultad;
    }

    public void setDificultad(int dificultad) {
        this.dificultad = dificultad;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
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
