package com.example.Gate_pass_system.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "dispatch")
public class Dispatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dispatch_id")
    private Long dispatchId;

    @ManyToOne
    @JoinColumn(name = "request_id", referencedColumnName = "ref_no")
    private GatePassRequest request;

    @ManyToOne
    @JoinColumn(name = "dispatched_by", referencedColumnName = "service_number")
    private User dispatchedBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private DispatchStatus status = DispatchStatus.DISPATCHED;

    @CreationTimestamp
    @Column(name = "dispatch_date")
    private LocalDateTime dispatchDate;

    @Column(name = "comments")
    private String comments;

    // Getters and Setters
    public Long getDispatchId() {
        return dispatchId;
    }

    public void setDispatchId(Long dispatchId) {
        this.dispatchId = dispatchId;
    }

    public GatePassRequest getRequest() {
        return request;
    }

    public void setRequest(GatePassRequest request) {
        this.request = request;
    }

    public User getDispatchedBy() {
        return dispatchedBy;
    }

    public void setDispatchedBy(User dispatchedBy) {
        this.dispatchedBy = dispatchedBy;
    }

    public DispatchStatus getStatus() {
        return status;
    }

    public void setStatus(DispatchStatus status) {
        this.status = status;
    }

    public LocalDateTime getDispatchDate() {
        return dispatchDate;
    }

    public void setDispatchDate(LocalDateTime dispatchDate) {
        this.dispatchDate = dispatchDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}