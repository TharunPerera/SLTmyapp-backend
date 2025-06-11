package com.example.Gate_pass_system.DTO;

import com.example.Gate_pass_system.entity.ApprovalStatus;

import java.time.LocalDateTime;

public class ApprovalDTO {
    private Integer approvalId;
    private Integer requestId;
    private String approvedBy;
    private ApprovalStatus approvalStatus;
    private LocalDateTime approvalDate;
    private String comments;

    // Constructor for creating new approval
    public ApprovalDTO(Integer requestId, String approvedBy, ApprovalStatus approvalStatus, String comments) {
        this.requestId = requestId;
        this.approvedBy = approvedBy;
        this.approvalStatus = approvalStatus;
        this.comments = comments;
        this.approvalDate = LocalDateTime.now();
    }

    public Integer getApprovalId() {
        return approvalId;
    }

    public void setApprovalId(Integer approvalId) {
        this.approvalId = approvalId;
    }

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public ApprovalStatus getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(ApprovalStatus approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public LocalDateTime getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(LocalDateTime approvalDate) {
        this.approvalDate = approvalDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}