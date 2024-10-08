package com.decrypto.api.decrypto.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

/**
 * Clase que representa un Mercado en el sistema.
 * Un Mercado está relacionado con un País y puede tener múltiples Comitentes.
 */
@Entity
public class Mercado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@NotEmpty
    private String codigo;

    @NotEmpty
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "pais_id", nullable = false)
    private Pais pais;

    @ManyToMany(mappedBy = "mercados")
    @JsonBackReference // Evita problemas de recursividad en la serialización
    private List<Comitente> comitentes = new ArrayList<>();
    
    private Double porcentaje;

    // Constructor vacío
    public Mercado() {}

    // Constructor con parámetros
    public Mercado(String codigo, String descripcion, Pais pais) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.pais = pais;
    }
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Pais getPais() {
        return pais;
    }

    public void setPais(Pais pais) {
        this.pais = pais;
    }

    public List<Comitente> getComitentes() {
        return comitentes;
    }

    public void setComitentes(List<Comitente> comitentes) {
        this.comitentes = comitentes;
    }
    
	public Double getPorcentaje() {
		return this.porcentaje;
	}

	public void setPorcentaje(Double porcentaje) {
		this.porcentaje = porcentaje;
	}

    @Override
    public String toString() {
        return "Mercado{id=" + id + ", codigo='" + codigo + '\'' + ", descripcion='" + descripcion + '\'' + ", pais=" + pais + '}';
    }


}
