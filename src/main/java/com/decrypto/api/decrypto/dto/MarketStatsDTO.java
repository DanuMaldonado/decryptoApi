package com.decrypto.api.decrypto.dto;

public class MarketStatsDTO {

   // private String marketName;
    private Double percentage;

    public MarketStatsDTO(Double percentage) {
        //this.setMarketName(marketName);
        this.setPercentage(percentage);
    }


	public Double getPercentage() {
		return percentage;
	}

	public void setPercentage(Double percentage) {
		this.percentage = percentage;
	}

}
