package com.decrypto.api.decrypto.dto;

import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO que contiene estadísticas de un país y sus mercados asociados.")
public class PaisStatsDTO {
	
    @Schema(description = "Nombre del país", example = "Argentina")
    private String country;
    
    @Schema(description = "Lista de mercados asociados con sus estadísticas. Cada entrada es un mapa donde la clave "
    		+ "es el nombre del mercado y el valor es un objeto MercadoStatsDTO.")
    private List<Map<String, MercadoStatsDTO>> market;
    
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public List<Map<String, MercadoStatsDTO>> getMarket() {
		return market;
	}
	public void setMarket(List<Map<String, MercadoStatsDTO>> market) {
		this.market = market;
	}

}
