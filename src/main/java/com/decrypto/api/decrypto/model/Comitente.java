package com.decrypto.api.decrypto.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotEmpty;


@Entity
public class Comitente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @NotEmpty // Añadir validación
    private String descripcion;

    @ManyToMany
    @JoinTable(
      name = "comitente_mercado", 
      joinColumns = @JoinColumn(name = "comitente_id"), 
      inverseJoinColumns = @JoinColumn(name = "mercado_id"))
    private List<Mercado> mercados = new ArrayList<>();

    // Constructor vacío
    public Comitente() {}

    // Constructor
    public Comitente(String descripcion, List<Mercado> mercados) {
        this.descripcion = descripcion;
        this.mercados = mercados;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Mercado> getMercados() {
        return mercados;
    }

    public void setMercados(List<Mercado> mercados) {
        this.mercados = mercados;
    }

    // Override toString(), equals(), and hashCode()
    @Override
    public String toString() {
        return "Comitente{id=" + id + ", descripcion='" + descripcion + '\'' + ", mercados=" + mercados + '}';
    }
}
