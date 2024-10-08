package com.decrypto.api.decrypto.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO para la entidad Mercado, contiene la información del mercado y el país asociado.")
public class MercadoDTO {
	
	@Schema(description = "ID único del mercado", example = "1")
    private Long id;
	
	@Schema(description = "Código del mercado", example = "MERC01")
    private String codigo;
	
    @Schema(description = "Descripción del mercado", example = "Mercado de Acciones")
    private String descripcion;
    
    @Schema(description = "ID del país asociado al mercado", example = "1")
    private Long paisId;
    
    @Schema(description = "Porcentaje del mercado respecto al total", example = "25.0")
    private Double porcentaje;

    // Constructor vacío
    public MercadoDTO() {}

    // Constructor con parámetros
    public MercadoDTO(Long id, String codigo, String descripcion, Long paisId, Double porcentaje) {
        this.id = id;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.paisId = paisId;
        this.porcentaje = porcentaje;
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

	public Double getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(Double porcentaje) {
		this.porcentaje = porcentaje;
	}

}
