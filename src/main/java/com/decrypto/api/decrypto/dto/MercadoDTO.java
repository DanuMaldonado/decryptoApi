package com.decrypto.api.decrypto.dto;

public class MercadoDTO {
    private Long id;
    private String codigo;
    private String descripcion;
    private Long paisId;

    // Constructor vacío
    public MercadoDTO() {}

    // Constructor con parámetros
    public MercadoDTO(Long id, String codigo, String descripcion, Long paisId) {
        this.id = id;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.paisId = paisId;
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

    public Long getPaisId() {
        return paisId;
    }

    public void setPaisId(Long paisId) {
        this.paisId = paisId;
    }
}
