package com.example.Gate_pass_system.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "receipts")
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "receipt_id")
    private Long receiptId;

    @ManyToOne
    @JoinColumn(name = "request_id", referencedColumnName = "ref_no")
    private GatePassRequest request;

    @ManyToOne
    @JoinColumn(name = "received_by", referencedColumnName = "service_number")
    private User receivedBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ReceiptStatus status = ReceiptStatus.RECEIVED;

    @CreationTimestamp
    @Column(name = "received_date")
    private LocalDateTime receivedDate;

    @Column(name = "comments")
    private String comments;

    // Getters and Setters
    public Long getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(Long receiptId) {
        this.receiptId = receiptId;
    }

    public GatePassRequest getRequest() {
        return request;
    }

    public void setRequest(GatePassRequest request) {
        this.request = request;
    }

    public User getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(User receivedBy) {
        this.receivedBy = receivedBy;
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