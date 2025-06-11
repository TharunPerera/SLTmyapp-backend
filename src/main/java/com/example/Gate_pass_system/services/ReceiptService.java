package com.example.Gate_pass_system.services;

import com.example.Gate_pass_system.DTO.ReceiptDetailsDTO;
import com.example.Gate_pass_system.DTO.ReceiptRequestDTO;
import com.example.Gate_pass_system.DTO.ReceiptResponseDTO;
import com.example.Gate_pass_system.DTO.ReceiptUserDTO;
import com.example.Gate_pass_system.entity.*;
import com.example.Gate_pass_system.repo.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReceiptService {
    @Autowired
    private ReceiptRepository receiptRepository;
    @Autowired
    private GatePassRequestRepository gatePassRequestRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ApprovalRepository approvalRepository;
    @Autowired
    private VerificationRepository verificationRepository;
    @Autowired
    private EmailService emailService;

    public Receipt receiveOrRejectRequest(ReceiptRequestDTO receiptDTO) {
        if (receiptDTO == null) {
            throw new IllegalArgumentException("Receipt DTO cannot be null");
        }
        GatePassRequest request = gatePassRequestRepository.findByRefNo(receiptDTO.getRequestId())
                .orElseThrow(() -> new RuntimeException("Request not found with refNo: " + receiptDTO.getRequestId()));
        boolean isFromColomboMain = request.getOutLocation().getLocationName().equals("Colombo Main");
        if (isFromColomboMain && !request.getStatus().equals("DutyOfficerApproved")) {
            throw new RuntimeException("Request must be approved by duty officer before receiving");
        } else if (!isFromColomboMain && !request.getStatus().equals("ExecutiveApproved")) {
            throw new RuntimeException("Request must be approved by executive officer before receiving");
        }
        if (!request.getReceiver().getServiceNumber().equals(receiptDTO.getReceivedBy())) {
            throw new RuntimeException("Only the designated receiver can mark this request as received");
        }
        User receivedBy = userRepository.findById(receiptDTO.getReceivedBy())
                .orElseThrow(() -> new RuntimeException("User not found with serviceNumber: " + receiptDTO.getReceivedBy()));
        Receipt receipt = receiptRepository.findByRequest_RefNo(request.getRefNo())
                .orElse(new Receipt());
        receipt.setRequest(request);
        receipt.setReceivedBy(receivedBy);
        receipt.setStatus(receiptDTO.getStatus());
        receipt.setComments(receiptDTO.getComments());
        receipt.setReceivedDate(LocalDateTime.now());
        String newStatus = receiptDTO.getStatus() == ReceiptStatus.RECEIVED ? "Received" : "ReceiverRejected";
        request.setStatus(newStatus);
        gatePassRequestRepository.save(request);
        Receipt savedReceipt = receiptRepository.save(receipt);
        try {
            String senderEmail = request.getSender().getEmail();
            String executiveEmail = request.getExecutiveOfficer().getEmail();
            String dutyOfficerEmail = isFromColomboMain ? verificationRepository.findByRequest_RefNo(request.getRefNo())
                    .map(Verification::getVerifiedBy)
                    .map(User::getEmail)
                    .orElse(null) : null;
            String requestId = String.valueOf(request.getRefNo());
            String comments = receiptDTO.getComments();
            String role = "Receiver";
            if (receiptDTO.getStatus() == ReceiptStatus.REJECTED) {
                emailService.sendRejectionEmail(senderEmail, requestId, role, comments, "Rejected");
                emailService.sendRejectionEmail(executiveEmail, requestId, role, comments, "Rejected");
                if (dutyOfficerEmail != null) {
                    emailService.sendRejectionEmail(dutyOfficerEmail, requestId, role, comments, "Rejected");
                }
            } else {
                emailService.sendApprovalEmail(senderEmail, requestId, role, comments);
                emailService.sendApprovalEmail(executiveEmail, requestId, role, comments);
                if (dutyOfficerEmail != null) {
                    emailService.sendApprovalEmail(dutyOfficerEmail, requestId, role, comments);
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }
        return savedReceipt;
    }

    public List<ReceiptResponseDTO> getReceiptsByEmployee(String employeeId) {
        if (employeeId == null || employeeId.trim().isEmpty()) {
            throw new IllegalArgumentException("Employee ID cannot be null or empty");
        }
        return receiptRepository.findByReceivedBy_ServiceNumber(employeeId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<GatePassRequest> getPendingReceipts(String receiverId) {
        if (receiverId == null || receiverId.trim().isEmpty()) {
            throw new IllegalArgumentException("Receiver ID cannot be null or empty");
        }
        List<GatePassRequest> pendingReceipts = new ArrayList<>();
        // Add requests from Colombo Main with DutyOfficerApproved status
        pendingReceipts.addAll(gatePassRequestRepository.findByStatusAndReceiver_ServiceNumber("DutyOfficerApproved", receiverId)
                .stream()
                .filter(request -> request.getOutLocation().getLocationName().equals("Colombo Main"))
                .collect(Collectors.toList()));
        // Add requests from sub-branches with ExecutiveApproved status
        pendingReceipts.addAll(gatePassRequestRepository.findByStatusAndReceiver_ServiceNumber("ExecutiveApproved", receiverId));
        return pendingReceipts;
    }

    public ReceiptDetailsDTO getReceiptDetailsByRequestId(String requestId) {
        if (requestId == null || requestId.trim().isEmpty()) {
            throw new IllegalArgumentException("Request ID cannot be null or empty");
        }
        Long requestIdLong;
        try {
            requestIdLong = Long.parseLong(requestId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Request ID must be a valid number", e);
        }
        GatePassRequest request = gatePassRequestRepository.findByRefNo(requestIdLong)
                .orElseThrow(() -> new RuntimeException("Request not found with refNo: " + requestId));
        ReceiptDetailsDTO detailsDTO = new ReceiptDetailsDTO();
        Optional<Approval> approvalOpt = approvalRepository.findByRequest_RefNo(requestIdLong);
        if (approvalOpt.isPresent()) {
            Approval approval = approvalOpt.get();
            User executiveOfficer = approval.getApprovedBy();
            if (executiveOfficer != null) {
                detailsDTO.setExecutiveOfficer(new ReceiptUserDTO(
                        executiveOfficer.getServiceNumber(),
                        getFullName(executiveOfficer)));
                detailsDTO.setExecutiveStatus(approval.getApprovalStatus().toString());
                detailsDTO.setExecutiveComments(approval.getComments());
                detailsDTO.setExecutiveDate(approval.getApprovalDate());
            }
        } else {
            User executiveOfficer = request.getExecutiveOfficer();
            if (executiveOfficer != null) {
                detailsDTO.setExecutiveOfficer(new ReceiptUserDTO(
                        executiveOfficer.getServiceNumber(),
                        getFullName(executiveOfficer)));
                detailsDTO.setExecutiveStatus(request.getStatus());
            }
        }
        Optional<Verification> verificationOpt = verificationRepository.findByRequest_RefNo(requestIdLong);
        if (verificationOpt.isPresent()) {
            Verification verification = verificationOpt.get();
            User dutyOfficer = verification.getVerifiedBy();
            if (dutyOfficer != null) {
                detailsDTO.setDutyOfficer(new ReceiptUserDTO(
                        dutyOfficer.getServiceNumber(),
                        getFullName(dutyOfficer)));
                detailsDTO.setDutyStatus(verification.getVerificationStatus().toString());
                detailsDTO.setDutyComments(verification.getComments());
                detailsDTO.setDutyDate(verification.getVerificationDate());
            }
        }
        Optional<Receipt> receiptOpt = receiptRepository.findByRequest_RefNo(requestIdLong);
        if (receiptOpt.isPresent()) {
            Receipt receipt = receiptOpt.get();
            User receiver = receipt.getReceivedBy();
            if (receiver != null) {
                detailsDTO.setReceiver(new ReceiptUserDTO(
                        receiver.getServiceNumber(),
                        getFullName(receiver)));
                detailsDTO.setReceiverStatus(receipt.getStatus().toString());
                detailsDTO.setReceiverComments(receipt.getComments());
                detailsDTO.setReceiverDate(receipt.getReceivedDate());
            }
        } else {
            User receiver = request.getReceiver();
            if (receiver != null) {
                detailsDTO.setReceiver(new ReceiptUserDTO(
                        receiver.getServiceNumber(),
                        getFullName(receiver)));
                detailsDTO.setReceiverStatus("Pending");
            }
        }
        return detailsDTO;
    }

    private String getFullName(User user) {
        if (user == null) return "N/A";
        String firstName = user.getFirstName() != null ? user.getFirstName() : "";
        String lastName = user.getLastName() != null ? user.getLastName() : "";
        String fullName = firstName + " " + lastName;
        return fullName.trim().isEmpty() ? user.getServiceNumber() : fullName.trim();
    }

    private ReceiptResponseDTO convertToDto(Receipt receipt) {
        if (receipt == null) {
            return null;
        }
        ReceiptResponseDTO dto = new ReceiptResponseDTO();
        dto.setReceiptId(receipt.getReceiptId());
        dto.setRequestId(receipt.getRequest().getRefNo());
        User receivedBy = receipt.getReceivedBy();
        if (receivedBy != null) {
            dto.setReceivedByName(getFullName(receivedBy));
        }
        dto.setStatus(receipt.getStatus());
        dto.setReceivedDate(receipt.getReceivedDate());
        dto.setComments(receipt.getComments());
        return dto;
    }
}