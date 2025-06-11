package com.example.Gate_pass_system.services;

import com.example.Gate_pass_system.DTO.CreateGatePassRequestDTO;
import com.example.Gate_pass_system.DTO.ItemDTO;
import com.example.Gate_pass_system.DTO.RequestListDTO;
import com.example.Gate_pass_system.entity.*;
import com.example.Gate_pass_system.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GatePassRequestService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GatePassRequestRepository gatePassRequestRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ItemCategoryRepository itemCategoryRepository;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private TransportationRepository transportationRepository;

    @Value("${file.max-images-per-item}")
    private int maxImagesPerItem;

    public GatePassRequest createGatePass(CreateGatePassRequestDTO requestDTO, List<MultipartFile> itemImages) throws Exception {
        // Validate sender, receiver, and executive officer
        User sender = userRepository.findById(requestDTO.getSenderServiceNumber())
                .orElseThrow(() -> new Exception("Sender not found"));
        User receiver = userRepository.findById(requestDTO.getReceiverServiceNumber())
                .orElseThrow(() -> new Exception("Receiver not found"));
        User executiveOfficer = userRepository.findById(requestDTO.getExecutiveOfficerServiceNumber())
                .orElseThrow(() -> new Exception("Executive Officer not found"));

        // Validate locations
        Location outLocation = locationRepository.findById(requestDTO.getOutLocationId())
                .orElseThrow(() -> new Exception("Out Location not found"));
        Location inLocation = locationRepository.findById(requestDTO.getInLocationId())
                .orElseThrow(() -> new Exception("In Location not found"));

        // Validate transportation details
        if (!requestDTO.getTransportMode().equals("BY_HAND") && !requestDTO.getTransportMode().equals("BY_VEHICLE")) {
            throw new Exception("Invalid transport mode");
        }
        if (requestDTO.getTransportMode().equals("BY_HAND")) {
            if (requestDTO.getIsSlt() == null) {
                throw new Exception("isSlt must be specified for BY_HAND transport");
            }
            if (requestDTO.getIsSlt() && (requestDTO.getServiceNumber() == null || requestDTO.getServiceNumber().isEmpty())) {
                throw new Exception("Service number required for SLT transport");
            }
            if (!requestDTO.getIsSlt() && (requestDTO.getTransporterName() == null || requestDTO.getPhoneNumber() == null || requestDTO.getNic() == null || requestDTO.getEmail() == null)) {
                throw new Exception("Transporter details required for Non-SLT transport");
            }
        } else if (requestDTO.getTransportMode().equals("BY_VEHICLE")) {
            if (requestDTO.getVehicleNumber() == null || requestDTO.getVehicleModel() == null) {
                throw new Exception("Vehicle details required for BY_VEHICLE transport");
            }
        }

        // Create and save Gate Pass Request
        GatePassRequest gatePassRequest = new GatePassRequest();
        gatePassRequest.setSender(sender);
        gatePassRequest.setReceiver(receiver);
        gatePassRequest.setExecutiveOfficer(executiveOfficer);
        gatePassRequest.setOutLocation(outLocation);
        gatePassRequest.setInLocation(inLocation);
        gatePassRequest.setCreatedDate(LocalDateTime.now());
        GatePassRequest savedRequest = gatePassRequestRepository.save(gatePassRequest);

        // Save transportation details
        Transportation transportation = new Transportation();
        transportation.setRequest(savedRequest);
        transportation.setTransportMode(requestDTO.getTransportMode());
        transportation.setIsSlt(requestDTO.getIsSlt());
        transportation.setServiceNumber(requestDTO.getServiceNumber());
        transportation.setTransporterName(requestDTO.getTransporterName());
        transportation.setPhoneNumber(requestDTO.getPhoneNumber());
        transportation.setNic(requestDTO.getNic());
        transportation.setEmail(requestDTO.getEmail());
        transportation.setVehicleNumber(requestDTO.getVehicleNumber());
        transportation.setVehicleModel(requestDTO.getVehicleModel());
        transportationRepository.save(transportation);

        // Save items with images
        for (int i = 0; i < requestDTO.getItems().size(); i++) {
            ItemDTO itemDTO = requestDTO.getItems().get(i);
            ItemCategory category = itemCategoryRepository.findById(itemDTO.getCategoryId())
                    .orElseThrow(() -> new Exception("Category not found with ID: " + itemDTO.getCategoryId()));
            Item item = new Item();
            item.setRequest(savedRequest);
            item.setCategory(category);
            item.setItemName(itemDTO.getItemName());
            item.setSerialNumber(itemDTO.getSerialNumber());
            item.setDescription(itemDTO.getDescription());
            item.setQuantity(itemDTO.getQuantity());
            item.setReturnable(itemDTO.getReturnable());
            item.setDueDate(itemDTO.getDueDate());
            item.setImages(new ArrayList<>());
            if (itemImages != null && !itemImages.isEmpty()) {
                int startIndex = i * maxImagesPerItem;
                int endIndex = Math.min(startIndex + maxImagesPerItem, itemImages.size());
                for (int j = startIndex; j < endIndex; j++) {
                    MultipartFile imageFile = itemImages.get(j);
                    String storedFileName = fileStorageService.storeFile(imageFile);
                    ItemImage itemImage = new ItemImage();
                    itemImage.setImagePath(storedFileName);
                    itemImage.setUploadOrder(j - startIndex);
                    itemImage.setItem(item);
                    item.getImages().add(itemImage);
                }
            }
            itemRepository.save(item);
        }
        return savedRequest;
    }

    public List<RequestListDTO> getPendingRequestsByExecutiveId(String executiveId) {
        List<GatePassRequest> requests = gatePassRequestRepository.findByExecutiveOfficer_ServiceNumberAndStatus(executiveId, "Pending");
        return requests.stream()
                .map(request -> {
                    RequestListDTO dto = new RequestListDTO();
                    dto.setRefNo(request.getRefNo());
                    dto.setCreatedDate(request.getCreatedDate());
                    if (request.getSender() != null) {
                        dto.setSenderName(request.getSender().getFirstName() + " " + request.getSender().getLastName());
                        dto.setSenderServiceNumber(request.getSender().getServiceNumber());
                    }
                    if (request.getReceiver() != null) {
                        dto.setReceiverName(request.getReceiver().getFirstName() + " " + request.getReceiver().getLastName());
                        dto.setReceiverServiceNumber(request.getReceiver().getServiceNumber());
                    }
                    dto.setStatus(request.getStatus());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}