package com.decrypto.api.decrypto.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description = "DTO para la entidad Comitente, contiene la descripción y los IDs de los mercados asociados.")
public class ComitenteDTO {
	
	@Schema(description = "ID único del comitente", example = "1")
    private Long id;
	
	@Schema(description = "Descripción del comitente", example = "Comitente MAE1")
    private String descripcion;
	
    @Schema(description = "Lista de IDs de los mercados asociados al comitente", example = "[1, 2, 3]")
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
