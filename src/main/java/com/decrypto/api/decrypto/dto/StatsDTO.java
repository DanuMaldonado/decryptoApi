package com.decrypto.api.decrypto.dto;

import java.util.List;

public class StatsDTO {
    
    private String country;
    private List<MarketStatsDTO> market;

    public StatsDTO(String country, List<MarketStatsDTO> market) {
        this.setCountry(country);
        this.setMarket(market);
    }

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public List<MarketStatsDTO> getMarket() {
		return market;
	}

	public void setMarket(List<MarketStatsDTO> market) {
		this.market = market;
	}
}