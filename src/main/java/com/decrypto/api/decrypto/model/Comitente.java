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


@Entity
public class Comitente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true)
    private String descripcion;

    public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@ManyToMany
    @JoinTable(
      name = "comitente_mercado", 
      joinColumns = @JoinColumn(name = "comitente_id"), 
      inverseJoinColumns = @JoinColumn(name = "mercado_id"))
    private List<Mercado> mercados = new ArrayList<>();
	
	public List<Mercado> getMercados() {
	    return mercados;
	}

}
