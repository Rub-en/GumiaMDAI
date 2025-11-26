package com.Gumia.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "receta")
public class Receta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descripcion;
    private String url_imagen;
    private int dificultad;
    private int tiempo;
    private String categoria;

    @Column(columnDefinition = "TEXT")
    private String preparacion; // <<--- NUEVO CAMPO

    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Usuario autor;

    @OneToMany(mappedBy = "receta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ingrediente> ingredientes = new ArrayList<>();

    @ManyToMany(mappedBy = "favoritos")
    private List<Usuario> usuariosQueLaTienenEnFavoritos = new ArrayList<>();

    public Receta() {}

    // Constructor original (por compatibilidad)
    public Receta(String titulo, String descripcion, int tiempo, int dificultad, String categoria, String url_imagen, Usuario autor) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.tiempo = tiempo;
        this.dificultad = dificultad;
        this.categoria = categoria;
        this.url_imagen = url_imagen;
        this.autor = autor;
    }

    // ⭐ Constructor NUEVO con preparación
    public Receta(String titulo, String descripcion, int tiempo, int dificultad, String categoria,
                  String url_imagen, Usuario autor, String preparacion) {

        this.titulo = titulo;
        this.descripcion = descripcion;
        this.tiempo = tiempo;
        this.dificultad = dificultad;
        this.categoria = categoria;
        this.url_imagen = url_imagen;
        this.autor = autor;
        this.preparacion = preparacion;
    }

    // Helper ingredientes
    public void addIngrediente(Ingrediente ingrediente) {
        ingredientes.add(ingrediente);
        ingrediente.setReceta(this);
    }

    // Getters y Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getUrl_imagen() { return url_imagen; }
    public void setUrl_imagen(String url_imagen) { this.url_imagen = url_imagen; }

    public int getDificultad() { return dificultad; }
    public void setDificultad(int dificultad) { this.dificultad = dificultad; }

    public int getTiempo() { return tiempo; }
    public void setTiempo(int tiempo) { this.tiempo = tiempo; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public Usuario getAutor() { return autor; }
    public void setAutor(Usuario autor) { this.autor = autor; }

    public List<Ingrediente> getIngredientes() { return ingredientes; }
    public void setIngredientes(List<Ingrediente> ingredientes) { this.ingredientes = ingredientes; }

    public List<Usuario> getUsuariosQueLaTienenEnFavoritos() { return usuariosQueLaTienenEnFavoritos; }
    public void setUsuariosQueLaTienenEnFavoritos(List<Usuario> usuarios) { this.usuariosQueLaTienenEnFavoritos = usuarios; }

    public String getPreparacion() { return preparacion; }
    public void setPreparacion(String preparacion) { this.preparacion = preparacion; }
}
