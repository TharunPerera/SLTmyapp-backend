package com.example.Gate_pass_system.controller;

import com.example.Gate_pass_system.DTO.ReceiptDetailsDTO;
import com.example.Gate_pass_system.DTO.ReceiptRequestDTO;
import com.example.Gate_pass_system.DTO.ReceiptResponse;
import com.example.Gate_pass_system.DTO.ReceiptResponseDTO;
import com.example.Gate_pass_system.entity.GatePassRequest;
import com.example.Gate_pass_system.entity.Receipt;
import com.example.Gate_pass_system.entity.User;
import com.example.Gate_pass_system.services.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/receipts")
public class ReceiptController {
    @Autowired
    private ReceiptService receiptService;

    @PostMapping
    public ResponseEntity<ReceiptResponse> submitReceipt(@RequestBody ReceiptRequestDTO receiptDTO) {
        try {
            Receipt receipt = receiptService.receiveOrRejectRequest(receiptDTO);
            ReceiptResponseDTO responseDTO = convertToDto(receipt);
            return ResponseEntity.ok(
                    new ReceiptResponse("Receipt processed successfully", responseDTO)
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new ReceiptResponse("Error: " + e.getMessage(), null)
            );
        }
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<?> getReceiptsByEmployee(@PathVariable String employeeId) {
        try {
            List<ReceiptResponseDTO> receipts = receiptService.getReceiptsByEmployee(employeeId);
            return ResponseEntity.ok(receipts);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/pending/{receiverId}")
    public ResponseEntity<?> getPendingReceipts(@PathVariable String receiverId) {
        try {
            List<GatePassRequest> pendingRequests = receiptService.getPendingReceipts(receiverId);
            return ResponseEntity.ok(pendingRequests);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/request/{requestId}")
    public ResponseEntity<?> getReceiptByRequestId(@PathVariable String requestId) {
        try {
            ReceiptDetailsDTO receiptDetails = receiptService.getReceiptDetailsByRequestId(requestId);
            return ResponseEntity.ok(receiptDetails);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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
            String fullName = (receivedBy.getFirstName() != null ? receivedBy.getFirstName() : "")
                    + " " +
                    (receivedBy.getLastName() != null ? receivedBy.getLastName() : "");
            dto.setReceivedByName(fullName.trim().isEmpty() ? receivedBy.getServiceNumber() : fullName.trim());
        }

        dto.setStatus(receipt.getStatus());
        dto.setReceivedDate(receipt.getReceivedDate());
        dto.setComments(receipt.getComments());
        return dto;
    }
}