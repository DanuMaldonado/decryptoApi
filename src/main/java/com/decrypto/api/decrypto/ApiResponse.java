package com.decrypto.api.decrypto;
public class ApiResponse {
    private boolean success;
    private String message;
    private String url;
    private String timestamp;

    public ApiResponse(boolean success, String message, String url, String timestamp) {
        this.success = success;
        this.message = message;
        this.url = url;
        this.timestamp = timestamp;
    }

    // Getters y setters
}
