package com.Gumia.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String email;
    private String password;

    // RELACIÓN 1:N (Un usuario publica muchas recetas)
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Receta> recetasPublicadas = new ArrayList<>();

    // RELACIÓN N:M (Favoritos)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "usuario_recetas_favoritas",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "receta_id")
    )
    private List<Receta> favoritos = new ArrayList<>();

    public Usuario() {
    }

    public Usuario(String nombre, String email, String password) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public List<Receta> getRecetasPublicadas() { return recetasPublicadas; }
    public void setRecetasPublicadas(List<Receta> recetasPublicadas) { this.recetasPublicadas = recetasPublicadas; }

    public List<Receta> getFavoritos() { return favoritos; }
    public void setFavoritos(List<Receta> favoritos) { this.favoritos = favoritos; }
}