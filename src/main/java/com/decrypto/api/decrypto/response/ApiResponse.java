package com.decrypto.api.decrypto.response;

import lombok.NoArgsConstructor;


@NoArgsConstructor
public class ApiResponse {
    private boolean success;
    private String message;
    private String url;
    private String timestamp;

    // Constructor adicional sin url
    public ApiResponse(boolean success, String message, String url, String timestamp) {
        this.success = success;
        this.message = message;
        this.url = url;
        this.timestamp = timestamp;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getUrl() {
        return url;
    }

    public String getTimestamp() {
        return timestamp;
    }
}

