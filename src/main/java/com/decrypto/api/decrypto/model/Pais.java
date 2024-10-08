package com.decrypto.api.decrypto.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

/**
 * Clase que representa un país en el sistema.
 */
@Entity
public class Pais {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull
    private NombrePais nombre;

    // Constructor vacío
    public Pais() {}

    // Constructor con parámetro
    public Pais(NombrePais nombre) {
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NombrePais getNombre() {
        return nombre;
    }

    public void setNombre(NombrePais nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Pais{id=" + id + ", nombre=" + nombre + '}';
    }

}