package com.example.Gate_pass_system.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "gate_pass_requests")
public class GatePassRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ref_no")
    private Long refNo;

    @ManyToOne
    @JoinColumn(name = "created_user_id", nullable = false)
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    @ManyToOne
    @JoinColumn(name = "executive_officer_id", nullable = false)
    private User executiveOfficer;

    @ManyToOne
    @JoinColumn(name = "out_location_id", nullable = false)
    private Location outLocation;

    @ManyToOne
    @JoinColumn(name = "in_location_id", nullable = false)
    private Location inLocation;

    @CreationTimestamp
    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column(name = "status")
    private String status = "Pending";

    // Getters and Setters
    public Long getRefNo() {
        return refNo;
    }

    public void setRefNo(Long refNo) {
        this.refNo = refNo;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public User getExecutiveOfficer() {
        return executiveOfficer;
    }

    public void setExecutiveOfficer(User executiveOfficer) {
        this.executiveOfficer = executiveOfficer;
    }

    public Location getOutLocation() {
        return outLocation;
    }

    public void setOutLocation(Location outLocation) {
        this.outLocation = outLocation;
    }

    public Location getInLocation() {
        return inLocation;
    }

    public void setInLocation(Location inLocation) {
        this.inLocation = inLocation;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}