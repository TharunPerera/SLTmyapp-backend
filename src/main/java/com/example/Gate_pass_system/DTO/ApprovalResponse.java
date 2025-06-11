package com.example.Gate_pass_system.DTO;

public class ApprovalResponse {
    private String message;
    private ApprovalResponseDTO data;

    // Make sure you have this constructor
    public ApprovalResponse(String message, ApprovalResponseDTO data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ApprovalResponseDTO getData() {
        return data;
    }

    public void setData(ApprovalResponseDTO data) {
        this.data = data;
    }
}
