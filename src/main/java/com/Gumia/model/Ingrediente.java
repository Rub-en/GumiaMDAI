package com.Gumia.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Ingrediente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private float cantidad;

    @ManyToOne(fetch = FetchType.LAZY) //Esto sirve para que no se inicialice hasta que haga falta. Nos lo dio DeepSeek y pareció útil, ya que habrá muchas tablas de estas
    @JoinColumn(name = "receta_id")
    private Receta receta;

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

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }
}
