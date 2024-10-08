package com.decrypto.api.decrypto.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO que contiene las estadísticas de un mercado, incluyendo su nombre y el porcentaje del total.")
public class MercadoStatsDTO {
	
    @Schema(description = "Nombre del mercado", example = "Mercado de Acciones")
	private String marketName;
    
    @Schema(description = "Porcentaje del mercado respecto al total", example = "25.0%")
    private String percentage;

    // Constructor con nombre del mercado y porcentaje
    public MercadoStatsDTO(String marketName, String percentage) {
        this.marketName = marketName;
        this.percentage = percentage;
    }
    
    // Constructor solo con el nombre del mercado
    public MercadoStatsDTO(String marketName) {
        this.marketName = marketName;
    }

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}

	public String getPercentage() {
		return percentage;
	}

	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}


}
