package com.example.Gate_pass_system.DTO;

import java.time.LocalDateTime;

public class RequestListDTO {

    private Long refNo;  // Changed from requestId to refNo to match your entity
    private LocalDateTime createdDate;
    private String senderName;
    private String senderServiceNumber;
    private String receiverName;
    private String receiverServiceNumber;
    private String status;

    public Long getRefNo() {
        return refNo;
    }

    public void setRefNo(Long refNo) {
        this.refNo = refNo;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderServiceNumber() {
        return senderServiceNumber;
    }

    public void setSenderServiceNumber(String senderServiceNumber) {
        this.senderServiceNumber = senderServiceNumber;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverServiceNumber() {
        return receiverServiceNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setReceiverServiceNumber(String receiverServiceNumber) {
        this.receiverServiceNumber = receiverServiceNumber;
    }
}
