package com.decrypto.api.decrypto.dto;

public class MarketStatsDTO {

    private String marketName;
    private Double percentage;

    public MarketStatsDTO(String marketName, Double percentage) {
        this.setMarketName(marketName);
        this.setPercentage(percentage);
    }

	public String getMarketName() {
		return marketName;
	}

	public void setMarketName(String marketName) {
		this.marketName = marketName;
	}

	public Double getPercentage() {
		return percentage;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}

    // Getters and setters...
}
