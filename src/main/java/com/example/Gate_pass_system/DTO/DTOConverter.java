package com.example.Gate_pass_system.DTO;

import com.example.Gate_pass_system.entity.Approval;
import com.example.Gate_pass_system.entity.User;

public class DTOConverter {
    public static ApprovalResponseDTO convertToDto(Approval approval) {
        ApprovalResponseDTO dto = new ApprovalResponseDTO();
        dto.setApprovalId(approval.getApprovalId());
        dto.setRequestId(approval.getRequest().getRefNo());

        User approvedBy = approval.getApprovedBy();
        String fullName = approvedBy.getFirstName() + " " + approvedBy.getLastName();
        dto.setApprovedByName(fullName);

        dto.setApprovalStatus(approval.getApprovalStatus());
        dto.setApprovalDate(approval.getApprovalDate());
        dto.setComments(approval.getComments());
        return dto;
    }
}