package com.example.Gate_pass_system.DTO;

import java.time.LocalDateTime;
import java.util.List;

public class RequestDetailsDTO {
    private Long refNo;
    private LocalDateTime createdDate;
    private String senderName;
    private String senderServiceNumber;
    private String receiverName;
    private String receiverServiceNumber;
    private String outLocation; // Expects String
    private String inLocation; // Expects String
    private String status;
    private List<ItemDetailDTO> items;
    private List<ApprovalResponseDTO> approvals;

    // Getters and Setters
    public Long getRefNo() { return refNo; }
    public void setRefNo(Long refNo) { this.refNo = refNo; }
    public LocalDateTime getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }
    public String getSenderName() { return senderName; }
    public void setSenderName(String senderName) { this.senderName = senderName; }
    public String getSenderServiceNumber() { return senderServiceNumber; }
    public void setSenderServiceNumber(String senderServiceNumber) { this.senderServiceNumber = senderServiceNumber; }
    public String getReceiverName() { return receiverName; }
    public void setReceiverName(String receiverName) { this.receiverName = receiverName; }
    public String getReceiverServiceNumber() { return receiverServiceNumber; }
    public void setReceiverServiceNumber(String receiverServiceNumber) { this.receiverServiceNumber = receiverServiceNumber; }
    public String getOutLocation() { return outLocation; }
    public void setOutLocation(String outLocation) { this.outLocation = outLocation; }
    public String getInLocation() { return inLocation; }
    public void setInLocation(String inLocation) { this.inLocation = inLocation; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public List<ItemDetailDTO> getItems() { return items; }
    public void setItems(List<ItemDetailDTO> items) { this.items = items; }
    public List<ApprovalResponseDTO> getApprovals() { return approvals; }
    public void setApprovals(List<ApprovalResponseDTO> approvals) { this.approvals = approvals; }
}