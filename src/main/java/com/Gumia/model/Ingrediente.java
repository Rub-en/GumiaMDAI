package com.Gumia.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ingrediente")
public class Ingrediente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String cantidad;

    // RELACIÓN N:1 (Muchos ingredientes pertenecen a una Receta)

    @ManyToOne
    @JoinColumn(name = "receta_id")
    private Receta receta;

    // Constructor vacío (obligatorio JPA)
    public Ingrediente() {
    }

    // Constructor para crear ingredientes fácilmente
    public Ingrediente(String nombre, String cantidad, Receta receta) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.receta = receta;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCantidad() { return cantidad; }
    public void setCantidad(String cantidad) { this.cantidad = cantidad; }

    public Receta getReceta() { return receta; }
    public void setReceta(Receta receta) { this.receta = receta; }
}