package com.decrypto.api.decrypto.dto;

import java.util.List;

public class ComitenteDTO {
    private Long id;
    private String descripcion;
    private List<Long> mercadoIds;

    // Constructor vacío
    public ComitenteDTO() {}

    // Constructor con parámetros
    public ComitenteDTO(Long id, String descripcion, List<Long> mercadoIds) {
        this.id = id;
        this.descripcion = descripcion;
        this.mercadoIds = mercadoIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Long> getMercadoIds() {
        return mercadoIds;
    }

    public void setMercadoIds(List<Long> mercadoIds) {
        this.mercadoIds = mercadoIds;
    }
}
