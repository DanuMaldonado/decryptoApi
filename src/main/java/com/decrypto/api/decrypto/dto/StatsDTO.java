package com.decrypto.api.decrypto.dto;

import java.util.List;
import java.util.Map;

import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description = "DTO que contiene estadísticas generales, agrupadas por país y mercados.")
public class StatsDTO {
	
    @Schema(description = "Nombre del país", example = "Argentina")
    private String country;
    
    @Schema(description = "Lista de mercados y sus porcentajes asociados. Cada mercado es un mapa donde la clave es "
    		+ "el nombre del mercado y el valor es otro mapa que contiene el nombre del mercado y su porcentaje.")
    private List<Map<String, Map<String, String>>> market;

    
    public StatsDTO(String country, List<Map<String, Map<String, String>>> market) {
        this.country = country;
        this.setMarket(market);
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

	public List<Map<String, Map<String, String>>> getMarket() {
		return market;
	}

	public void setMarket(List<Map<String, Map<String, String>>> market) {
		this.market = market;
	}


}
