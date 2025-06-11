package com.example.Gate_pass_system.controller;
import com.example.Gate_pass_system.DTO.ApprovalRequestDTO;
import com.example.Gate_pass_system.DTO.ApprovalResponse;
import com.example.Gate_pass_system.DTO.ApprovalResponseDTO;
import com.example.Gate_pass_system.entity.Approval;
import com.example.Gate_pass_system.entity.User;
import com.example.Gate_pass_system.services.ApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/approvals")
public class ApprovalController {
    @Autowired
    private ApprovalService approvalService;

    @PostMapping
    public ResponseEntity<ApprovalResponse> submitApproval(@RequestBody ApprovalRequestDTO approvalDTO) {
        try {
            // Debug: print incoming DTO
            System.out.println("Received approval DTO: " + approvalDTO);

            Approval approval = approvalService.approveOrRejectRequest(approvalDTO);

            if (approval == null) {
                throw new RuntimeException("Approval service returned null");
            }

            // Debug: print approval object
            System.out.println("Approval object: " + approval);

            ApprovalResponseDTO responseDTO = convertToDto(approval);

            if (responseDTO == null) {
                throw new RuntimeException("DTO conversion returned null");
            }

            // Debug: print converted DTO
            System.out.println("Converted DTO: " + responseDTO);

            return ResponseEntity.ok(
                    new ApprovalResponse("Approval processed successfully", responseDTO)
            );

        } catch (Exception e) {
            // Log the full error
            e.printStackTrace();
            return ResponseEntity.badRequest().body(
                    new ApprovalResponse("Error: " + e.getMessage(), null)
            );
        }
    }
    @GetMapping("/executive/{executiveId}")
    public ResponseEntity<?> getApprovalsByExecutive(@PathVariable String executiveId) {
        try {
            List<ApprovalResponseDTO> approvals = approvalService.getApprovalsByExecutive(executiveId);
            return ResponseEntity.ok(approvals);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private ApprovalResponseDTO convertToDto(Approval approval) {
        if (approval == null) {
            return null;
        }

        ApprovalResponseDTO dto = new ApprovalResponseDTO();
        try {
            dto.setApprovalId(approval.getApprovalId());
            dto.setRequestId(approval.getRequest().getRefNo());

            User approvedBy = approval.getApprovedBy();
            if (approvedBy != null) {
                String fullName = (approvedBy.getFirstName() != null ? approvedBy.getFirstName() : "")
                        + " " +
                        (approvedBy.getLastName() != null ? approvedBy.getLastName() : "");
                dto.setApprovedByName(fullName.trim());
            }

            dto.setApprovalStatus(approval.getApprovalStatus());
            dto.setApprovalDate(approval.getApprovalDate());
            dto.setComments(approval.getComments());
        } catch (Exception e) {
            // Log the error
            System.err.println("Error converting approval to DTO: " + e.getMessage());
            return null;
        }

        return dto;
    }
}