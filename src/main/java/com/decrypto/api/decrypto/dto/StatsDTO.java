package com.decrypto.api.decrypto.dto;

import java.util.List;
import java.util.Map;

public class StatsDTO {
    
    private String country;
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
