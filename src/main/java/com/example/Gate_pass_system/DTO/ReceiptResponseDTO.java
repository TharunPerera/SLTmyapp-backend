package com.example.Gate_pass_system.DTO;

import com.example.Gate_pass_system.entity.ReceiptStatus;

import java.time.LocalDateTime;

// Response DTO
public class ReceiptResponseDTO {
    private Long receiptId;
    private Long requestId;
    private String receivedByName;
    private ReceiptStatus status;
    private LocalDateTime receivedDate;
    private String comments;

    // Getters and Setters
    public Long getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(Long receiptId) {
        this.receiptId = receiptId;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public String getReceivedByName() {
        return receivedByName;
    }

    public void setReceivedByName(String receivedByName) {
        this.receivedByName = receivedByName;
    }

    public ReceiptStatus getStatus() {
        return status;
    }

    public void setStatus(ReceiptStatus status) {
        this.status = status;
    }

    public LocalDateTime getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(LocalDateTime receivedDate) {
        this.receivedDate = receivedDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}