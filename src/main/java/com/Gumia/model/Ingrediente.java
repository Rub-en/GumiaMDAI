package com.Gumia.model;

import jakarta.persistence.*;

@Entity
public class Ingrediente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String cantidad;

    @ManyToOne
    private Receta receta;

    // Constructor vacio (obligatorio para JPA)
    public Ingrediente() {
    }

    // Constructor con todos los campos
    public Ingrediente(Long id, String nombre, String cantidad, Receta receta) {
        this.id = id;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.receta = receta;
    }

    // Constructor sin ID (para crear nuevos ingredientes)
    public Ingrediente(String nombre, String cantidad, Receta receta) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.receta = receta;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public Receta getReceta() {
        return receta;
    }

    public void setReceta(Receta receta) {
        this.receta = receta;
    }
}
