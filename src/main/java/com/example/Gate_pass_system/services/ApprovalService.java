package com.example.Gate_pass_system.services;

import com.example.Gate_pass_system.DTO.ApprovalRequestDTO;
import com.example.Gate_pass_system.DTO.ApprovalResponseDTO;
import com.example.Gate_pass_system.entity.Approval;
import com.example.Gate_pass_system.entity.ApprovalStatus;
import com.example.Gate_pass_system.entity.GatePassRequest;
import com.example.Gate_pass_system.entity.User;
import com.example.Gate_pass_system.repo.ApprovalRepository;
import com.example.Gate_pass_system.repo.GatePassRequestRepository;
import com.example.Gate_pass_system.repo.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ApprovalService {

    @Autowired
    private ApprovalRepository approvalRepository;

    @Autowired
    private GatePassRequestRepository gatePassRequestRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    public Approval approveOrRejectRequest(ApprovalRequestDTO approvalDTO) {
        // Validate input
        if (approvalDTO == null) {
            throw new IllegalArgumentException("Approval DTO cannot be null");
        }

        // Find the gate pass request
        GatePassRequest request = gatePassRequestRepository.findByRefNo(approvalDTO.getRequestId())
                .orElseThrow(() -> new RuntimeException("Request not found with refNo: " + approvalDTO.getRequestId()));

        // Find the approving user
        User approvedBy = userRepository.findById(approvalDTO.getApprovedBy())
                .orElseThrow(() -> new RuntimeException("User not found with serviceNumber: " + approvalDTO.getApprovedBy()));

        // Create or update approval
        Approval approval = approvalRepository.findByRequest_RefNo(request.getRefNo())
                .orElse(new Approval());

        approval.setRequest(request);
        approval.setApprovedBy(approvedBy);
        approval.setApprovalStatus(approvalDTO.getApprovalStatus());
        approval.setComments(approvalDTO.getComments());
        approval.setApprovalDate(LocalDateTime.now());

        // Update gate pass request status
        String newStatus = approvalDTO.getApprovalStatus() == ApprovalStatus.APPROVED ? "ExecutiveApproved" : "ExecutiveRejected";
        request.setStatus(newStatus);

        // Save changes
        gatePassRequestRepository.save(request);
        Approval savedApproval = approvalRepository.save(approval);

        // Send email notifications
        try {
            String senderEmail = request.getSender().getEmail();
            String requestId = String.valueOf(request.getRefNo());
            String comments = approvalDTO.getComments();
            String role = "Executive Officer";

            if (approvalDTO.getApprovalStatus() == ApprovalStatus.REJECTED) {
                // Send rejection email to sender
                emailService.sendRejectionEmail(senderEmail, requestId, role, comments, "Rejected");
            } else {
                // Send approval email to sender
                emailService.sendApprovalEmail(senderEmail, requestId, role, comments);
            }
        } catch (Exception e) {
            // Log email sending error but don't fail the approval process
            System.err.println("Failed to send email: " + e.getMessage());
        }

        return savedApproval;
    }

    public List<ApprovalResponseDTO> getApprovalsByExecutive(String executiveId) {
        if (executiveId == null || executiveId.trim().isEmpty()) {
            throw new IllegalArgumentException("Executive ID cannot be null or empty");
        }

        return approvalRepository.findByApprovedBy_ServiceNumber(executiveId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private ApprovalResponseDTO convertToDto(Approval approval) {
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