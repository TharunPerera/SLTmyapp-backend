package com.example.Gate_pass_system.services;

import com.example.Gate_pass_system.DTO.VerificationDetailsDTO;
import com.example.Gate_pass_system.DTO.VerificationRequestDTO;
import com.example.Gate_pass_system.DTO.VerificationResponseDTO;
import com.example.Gate_pass_system.entity.Verification;
import com.example.Gate_pass_system.entity.VerificationStatus;
import com.example.Gate_pass_system.entity.GatePassRequest;
import com.example.Gate_pass_system.entity.User;
import com.example.Gate_pass_system.repo.VerificationRepository;
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
public class VerificationService {
    @Autowired
    private VerificationRepository verificationRepository;
    @Autowired
    private GatePassRequestRepository gatePassRequestRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;

    public Verification verifyOrRejectRequest(VerificationRequestDTO verificationDTO) {
        if (verificationDTO == null) {
            throw new IllegalArgumentException("Verification DTO cannot be null");
        }
        GatePassRequest request = gatePassRequestRepository.findByRefNo(verificationDTO.getRequestId())
                .orElseThrow(() -> new RuntimeException("Request not found with refNo: " + verificationDTO.getRequestId()));
        User verifiedBy = userRepository.findById(verificationDTO.getVerifiedBy())
                .orElseThrow(() -> new RuntimeException("User not found with serviceNumber: " + verificationDTO.getVerifiedBy()));
        if (!request.getStatus().equals("ExecutiveApproved")) {
            throw new RuntimeException("Request must be approved by executive officer before verification");
        }
        Verification verification = verificationRepository.findByRequest_RefNo(request.getRefNo())
                .orElse(new Verification());
        verification.setRequest(request);
        verification.setVerifiedBy(verifiedBy);
        verification.setVerificationStatus(verificationDTO.getVerificationStatus());
        verification.setComments(verificationDTO.getComments());
        verification.setVerificationDate(LocalDateTime.now());
        String newStatus = verificationDTO.getVerificationStatus() == VerificationStatus.VERIFIED ? "DutyOfficerApproved" : "DutyOfficerRejected";
        request.setStatus(newStatus);
        gatePassRequestRepository.save(request);
        Verification savedVerification = verificationRepository.save(verification);
        try {
            String senderEmail = request.getSender().getEmail();
            String executiveEmail = request.getExecutiveOfficer().getEmail();
            String requestId = String.valueOf(request.getRefNo());
            String comments = verificationDTO.getComments();
            String role = "Duty Officer";
            if (verificationDTO.getVerificationStatus() == VerificationStatus.REJECTED) {
                emailService.sendRejectionEmail(senderEmail, requestId, role, comments, "Rejected");
                emailService.sendRejectionEmail(executiveEmail, requestId, role, comments, "Rejected");
            } else {
                emailService.sendApprovalEmail(senderEmail, requestId, role, comments);
                emailService.sendApprovalEmail(executiveEmail, requestId, role, comments);
            }
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }
        return savedVerification;
    }

    public List<VerificationResponseDTO> getVerificationsByDutyOfficer(String dutyOfficerId) {
        if (dutyOfficerId == null || dutyOfficerId.trim().isEmpty()) {
            throw new IllegalArgumentException("Duty Officer ID cannot be null or empty");
        }
        return verificationRepository.findByVerifiedBy_ServiceNumber(dutyOfficerId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<GatePassRequest> getPendingVerifications() {
        return gatePassRequestRepository.findByStatus("ExecutiveApproved");
    }

    public VerificationDetailsDTO getVerificationDetailsByRequestId(String requestId) {
        return new VerificationDetailsDTO(); // Implement as needed
    }

    private VerificationResponseDTO convertToDto(Verification verification) {
        if (verification == null) {
            return null;
        }
        VerificationResponseDTO dto = new VerificationResponseDTO();
        dto.setVerificationId(verification.getVerificationId());
        dto.setRequestId(verification.getRequest().getRefNo());
        User verifiedBy = verification.getVerifiedBy();
        if (verifiedBy != null) {
            String fullName = (verifiedBy.getFirstName() != null ? verifiedBy.getFirstName() : "") + " " +
                    (verifiedBy.getLastName() != null ? verifiedBy.getLastName() : "");
            dto.setVerifiedByName(fullName.trim());
        }
        dto.setVerificationStatus(verification.getVerificationStatus());
        dto.setVerificationDate(verification.getVerificationDate());
        dto.setComments(verification.getComments());
        return dto;
    }
}