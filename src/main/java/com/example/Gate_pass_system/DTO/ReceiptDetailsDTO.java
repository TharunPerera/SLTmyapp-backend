package com.example.Gate_pass_system.DTO;

import java.time.LocalDateTime;

// Details DTO
public class ReceiptDetailsDTO {
    private ReceiptUserDTO receiver;
    private String receiverStatus;
    private String receiverComments;
    private LocalDateTime receiverDate;

    // Including all previous officers' info for completeness
    private ReceiptUserDTO executiveOfficer;
    private String executiveStatus;
    private String executiveComments;
    private LocalDateTime executiveDate;

    private ReceiptUserDTO dutyOfficer;
    private String dutyStatus;
    private String dutyComments;
    private LocalDateTime dutyDate;

    private ReceiptUserDTO securityOfficer;
    private String securityStatus;
    private String securityComments;
    private LocalDateTime securityDate;

    // Getters and Setters
    public ReceiptUserDTO getReceiver() {
        return receiver;
    }

    public void setReceiver(ReceiptUserDTO receiver) {
        this.receiver = receiver;
    }

    public String getReceiverStatus() {
        return receiverStatus;
    }

    public void setReceiverStatus(String receiverStatus) {
        this.receiverStatus = receiverStatus;
    }

    public String getReceiverComments() {
        return receiverComments;
    }

    public void setReceiverComments(String receiverComments) {
        this.receiverComments = receiverComments;
    }

    public LocalDateTime getReceiverDate() {
        return receiverDate;
    }

    public void setReceiverDate(LocalDateTime receiverDate) {
        this.receiverDate = receiverDate;
    }

    public ReceiptUserDTO getExecutiveOfficer() {
        return executiveOfficer;
    }

    public void setExecutiveOfficer(ReceiptUserDTO executiveOfficer) {
        this.executiveOfficer = executiveOfficer;
    }

    public String getExecutiveStatus() {
        return executiveStatus;
    }

    public void setExecutiveStatus(String executiveStatus) {
        this.executiveStatus = executiveStatus;
    }

    public String getExecutiveComments() {
        return executiveComments;
    }

    public void setExecutiveComments(String executiveComments) {
        this.executiveComments = executiveComments;
    }

    public LocalDateTime getExecutiveDate() {
        return executiveDate;
    }

    public void setExecutiveDate(LocalDateTime executiveDate) {
        this.executiveDate = executiveDate;
    }

    public ReceiptUserDTO getDutyOfficer() {
        return dutyOfficer;
    }

    public void setDutyOfficer(ReceiptUserDTO dutyOfficer) {
        this.dutyOfficer = dutyOfficer;
    }

    public String getDutyStatus() {
        return dutyStatus;
    }

    public void setDutyStatus(String dutyStatus) {
        this.dutyStatus = dutyStatus;
    }

    public String getDutyComments() {
        return dutyComments;
    }

    public void setDutyComments(String dutyComments) {
        this.dutyComments = dutyComments;
    }

    public LocalDateTime getDutyDate() {
        return dutyDate;
    }

    public void setDutyDate(LocalDateTime dutyDate) {
        this.dutyDate = dutyDate;
    }

    public ReceiptUserDTO getSecurityOfficer() {
        return securityOfficer;
    }

    public void setSecurityOfficer(ReceiptUserDTO securityOfficer) {
        this.securityOfficer = securityOfficer;
    }

    public String getSecurityStatus() {
        return securityStatus;
    }

    public void setSecurityStatus(String securityStatus) {
        this.securityStatus = securityStatus;
    }

    public String getSecurityComments() {
        return securityComments;
    }

    public void setSecurityComments(String securityComments) {
        this.securityComments = securityComments;
    }

    public LocalDateTime getSecurityDate() {
        return securityDate;
    }

    public void setSecurityDate(LocalDateTime securityDate) {
        this.securityDate = securityDate;
    }
}