package com.example.Gate_pass_system.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "verifications")
public class Verification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "verification_id")
    private Long verificationId;

    @ManyToOne
    @JoinColumn(name = "request_id", referencedColumnName = "ref_no")
    private GatePassRequest request;

    @ManyToOne
    @JoinColumn(name = "verified_by", referencedColumnName = "service_number")
    private User verifiedBy;

    @Enumerated(EnumType.STRING)
    @Column(name = "verification_status")
    private VerificationStatus verificationStatus = VerificationStatus.VERIFIED;

    @CreationTimestamp
    @Column(name = "verification_date")
    private LocalDateTime verificationDate;

    @Column(name = "comments")
    private String comments;

    // Getters and Setters
    public Long getVerificationId() {
        return verificationId;
    }

    public void setVerificationId(Long verificationId) {
        this.verificationId = verificationId;
    }

    public GatePassRequest getRequest() {
        return request;
    }

    public void setRequest(GatePassRequest request) {
        this.request = request;
    }

    public User getVerifiedBy() {
        return verifiedBy;
    }

    public void setVerifiedBy(User verifiedBy) {
        this.verifiedBy = verifiedBy;
    }

    public VerificationStatus getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(VerificationStatus verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public LocalDateTime getVerificationDate() {
        return verificationDate;
    }

    public void setVerificationDate(LocalDateTime verificationDate) {
        this.verificationDate = verificationDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}