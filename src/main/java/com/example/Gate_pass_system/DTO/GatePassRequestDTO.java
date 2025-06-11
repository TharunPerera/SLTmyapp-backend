package com.example.Gate_pass_system.DTO;

import java.time.LocalDateTime;

public class GatePassRequestDTO {
    private Long id;
    private String refNo;
    private LocalDateTime createdDate;
    private String senderServiceNumber;
    private String receiverServiceNumber;
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getSenderServiceNumber() {
        return senderServiceNumber;
    }

    public void setSenderServiceNumber(String senderServiceNumber) {
        this.senderServiceNumber = senderServiceNumber;
    }

    public String getReceiverServiceNumber() {
        return receiverServiceNumber;
    }

    public void setReceiverServiceNumber(String receiverServiceNumber) {
        this.receiverServiceNumber = receiverServiceNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
// Getters and setters
}