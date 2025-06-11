package com.example.Gate_pass_system.controller;

import com.example.Gate_pass_system.DTO.*;
import com.example.Gate_pass_system.entity.GatePassRequest;
import com.example.Gate_pass_system.entity.User;
import com.example.Gate_pass_system.entity.Verification;
import com.example.Gate_pass_system.services.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/verifications")
public class VerificationController {
    @Autowired
    private VerificationService verificationService;

    @PostMapping
    public ResponseEntity<VerificationResponse> submitVerification(@RequestBody VerificationRequestDTO verificationDTO) {
        try {
            Verification verification = verificationService.verifyOrRejectRequest(verificationDTO);
            VerificationResponseDTO responseDTO = convertToDto(verification);
            return ResponseEntity.ok(
                    new VerificationResponse("Verification processed successfully", responseDTO)
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new VerificationResponse("Error: " + e.getMessage(), null)
            );
        }
    }

    @GetMapping("/duty-officer/{dutyOfficerId}")
    public ResponseEntity<?> getVerificationsByDutyOfficer(@PathVariable String dutyOfficerId) {
        try {
            List<VerificationResponseDTO> verifications = verificationService.getVerificationsByDutyOfficer(dutyOfficerId);
            return ResponseEntity.ok(verifications);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/pending")
    public ResponseEntity<?> getPendingVerifications() {
        try {
            List<GatePassRequest> pendingRequests = verificationService.getPendingVerifications();
            return ResponseEntity.ok(pendingRequests);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/request/{requestId}")
    public ResponseEntity<?> getVerificationByRequestId(@PathVariable String requestId) {
        try {
            VerificationDetailsDTO verificationDetails = verificationService.getVerificationDetailsByRequestId(requestId);
            return ResponseEntity.ok(verificationDetails);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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
            String fullName = (verifiedBy.getFirstName() != null ? verifiedBy.getFirstName() : "")
                    + " " +
                    (verifiedBy.getLastName() != null ? verifiedBy.getLastName() : "");
            dto.setVerifiedByName(fullName.trim());
        }

        dto.setVerificationStatus(verification.getVerificationStatus());
        dto.setVerificationDate(verification.getVerificationDate());
        dto.setComments(verification.getComments());
        return dto;
    }
}