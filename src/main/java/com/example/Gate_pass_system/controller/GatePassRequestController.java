package com.example.Gate_pass_system.controller;

import com.example.Gate_pass_system.DTO.*;
import com.example.Gate_pass_system.entity.*;
import com.example.Gate_pass_system.repo.*;
import com.example.Gate_pass_system.services.FileStorageService;
import com.example.Gate_pass_system.services.GatePassRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/gatepass")
public class GatePassRequestController {

    @Autowired
    private GatePassRequestService gatePassRequestService;

    @Autowired
    private GatePassRequestRepository gatePassRequestRepository;

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ApprovalRepository approvalRepository;

    @Autowired
    private ItemRepository itemRepository;

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createGatePass(
            @RequestPart("request") CreateGatePassRequestDTO requestDTO,
            @RequestPart(value = "itemImages", required = false) List<MultipartFile> itemImages) {
        try {
            GatePassRequest createdRequest = gatePassRequestService.createGatePass(requestDTO, itemImages);
            return ResponseEntity.ok("Gate Pass Created Successfully. Reference No: " + createdRequest.getRefNo());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/items/images/{fileName:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String fileName) {
        Resource file = fileStorageService.loadFileAsResource(fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @GetMapping("/request/{requestId}")
    public ResponseEntity<?> getRequestDetails(@PathVariable Long requestId) {
        try {
            // Get the gate pass request with proper error handling
            GatePassRequest request = gatePassRequestRepository.findById(requestId)
                    .orElseThrow(() -> new Exception("Request not found with ID: " + requestId));

            // Get all items with their images
            List<Item> items = itemRepository.findByRequest_RefNo(requestId);

            // Convert items to ItemDetailDTO with null checks
            List<ItemDetailDTO> itemDTOs = items.stream().map(item -> {
                ItemDetailDTO dto = new ItemDetailDTO();
                dto.setId(item.getItemId());
                dto.setItemName(item.getItemName());
                dto.setSerialNumber(item.getSerialNumber());
                dto.setDescription(item.getDescription());
                dto.setQuantity(item.getQuantity());
                dto.setReturnable(item.getReturnable());
                dto.setDueDate(item.getDueDate());

                // Set category name if exists
                if (item.getCategory() != null) {
                    dto.setCategoryName(item.getCategory().getCategoryName());
                }

                // Convert images to URLs with null check
                if (item.getImages() != null) {
                    List<String> imageUrls = item.getImages().stream()
                            .map(ItemImage::getImagePath)
                            .collect(Collectors.toList());
                    dto.setImageUrls(imageUrls);
                }

                return dto;
            }).collect(Collectors.toList());

            // Get approvals
            Optional<Approval> approvalOpt = approvalRepository.findByRequest_RefNo(requestId);
            List<ApprovalResponseDTO> approvalDTOs = approvalOpt.map(approval -> {
                ApprovalResponseDTO dto = new ApprovalResponseDTO();
                dto.setApprovalId(approval.getApprovalId());
                dto.setRequestId(approval.getRequest().getRefNo());

                // Null check for approvedBy
                if (approval.getApprovedBy() != null) {
                    dto.setApprovedByName(
                            approval.getApprovedBy().getFirstName() + " " +
                                    approval.getApprovedBy().getLastName());
                }

                dto.setApprovalStatus(approval.getApprovalStatus());
                dto.setApprovalDate(approval.getApprovalDate());
                dto.setComments(approval.getComments());
                return dto;
            }).map(List::of).orElse(List.of());

            // Build the response DTO with null checks
            RequestDetailsDTO response = new RequestDetailsDTO();
            response.setRefNo(request.getRefNo());
            response.setCreatedDate(request.getCreatedDate());

            // Null checks for sender and receiver
            if (request.getSender() != null) {
                response.setSenderName(
                        request.getSender().getFirstName() + " " +
                                request.getSender().getLastName());
                response.setSenderServiceNumber(request.getSender().getServiceNumber());
            }

            if (request.getReceiver() != null) {
                response.setReceiverName(
                        request.getReceiver().getFirstName() + " " +
                                request.getReceiver().getLastName());
                response.setReceiverServiceNumber(request.getReceiver().getServiceNumber());
            }

            // Set location names instead of Location objects
            response.setOutLocation(request.getOutLocation() != null ? request.getOutLocation().getLocationName() : null);
            response.setInLocation(request.getInLocation() != null ? request.getInLocation().getLocationName() : null);
            response.setStatus(request.getStatus());
            response.setItems(itemDTOs);
            response.setApprovals(approvalDTOs);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/pending/executive/{executiveId}")
    public ResponseEntity<?> getPendingRequestsByExecutive(@PathVariable String executiveId) {
        try {
            List<RequestListDTO> pendingRequests = gatePassRequestService.getPendingRequestsByExecutiveId(executiveId);
            return ResponseEntity.ok(pendingRequests);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/requests")
    public ResponseEntity<?> getAllRequestsForUser(
            @RequestParam(required = false) String user,
            @RequestParam(required = false) String status) {
        try {
            List<GatePassRequest> requests;
            if (user != null) {
                requests = gatePassRequestRepository.findBySenderServiceNumber(user);
            } else if (status != null) {
                requests = gatePassRequestRepository.findByStatus(status);
            } else {
                requests = gatePassRequestRepository.findAll();
            }

            List<RequestListDTO> dtos = requests.stream()
                    .map(request -> {
                        RequestListDTO dto = new RequestListDTO();
                        dto.setRefNo(request.getRefNo());
                        dto.setCreatedDate(request.getCreatedDate());
                        dto.setStatus(request.getStatus());

                        if (request.getSender() != null) {
                            dto.setSenderName(request.getSender().getFirstName() + " " + request.getSender().getLastName());
                            dto.setSenderServiceNumber(request.getSender().getServiceNumber());
                        }

                        if (request.getReceiver() != null) {
                            dto.setReceiverName(request.getReceiver().getFirstName() + " " + request.getReceiver().getLastName());
                            dto.setReceiverServiceNumber(request.getReceiver().getServiceNumber());
                        }

                        return dto;
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/requests/by-user/{serviceNumber}")
    public ResponseEntity<?> getRequestsByUser(@PathVariable String serviceNumber) {
        try {
            List<Long> requestIds = gatePassRequestRepository.findBySenderServiceNumber(serviceNumber)
                    .stream()
                    .map(GatePassRequest::getRefNo)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(requestIds);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}