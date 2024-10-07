package com.decrypto.api.decrypto.dto;

public class MercadoStatsDTO {
	
	private String marketName;
    private String percentage;

    public MercadoStatsDTO(String marketName, String percentage) {
        this.marketName = marketName;
        this.percentage = percentage;
    }
    
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
