package com.example.Gate_pass_system.DTO;

public class ReceiptResponse {
    private String message;
    private ReceiptResponseDTO data;

    public ReceiptResponse(String message, ReceiptResponseDTO data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ReceiptResponseDTO getData() {
        return data;
    }

    public void setData(ReceiptResponseDTO data) {
        this.data = data;
    }
}