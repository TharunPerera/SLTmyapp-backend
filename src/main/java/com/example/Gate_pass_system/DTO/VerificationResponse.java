package com.example.Gate_pass_system.DTO;

// Response wrapper for verification
public class VerificationResponse {
    private String message;
    private VerificationResponseDTO data;

    public VerificationResponse(String message, VerificationResponseDTO data) {
        this.message = message;
        this.data = data;
    }

    // Getters and Setters

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public VerificationResponseDTO getData() {
        return data;
    }

    public void setData(VerificationResponseDTO data) {
        this.data = data;
    }
}