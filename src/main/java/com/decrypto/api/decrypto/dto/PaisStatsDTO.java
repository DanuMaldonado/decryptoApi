package com.decrypto.api.decrypto.dto;

import java.util.List;
import java.util.Map;

public class PaisStatsDTO {
	
    private String country;
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
