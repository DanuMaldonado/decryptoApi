package com.decrypto.api.decrypto.dto;

import java.util.List;
import java.util.Map;

public class CountryStatsDTO {
    private String country;
    private List<Map<String, MarketStatsDTO>> market;

    public CountryStatsDTO(String country, List<Map<String, MarketStatsDTO>> market) {
        this.setCountry(country);
        this.setMarket(market);
    }

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public List<Map<String, MarketStatsDTO>> getMarket() {
		return market;
	}

	public void setMarket(List<Map<String, MarketStatsDTO>> market) {
		this.market = market;
	}

    // Getters y Setters
}
