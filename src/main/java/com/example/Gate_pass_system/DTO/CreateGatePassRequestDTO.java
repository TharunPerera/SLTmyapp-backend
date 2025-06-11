package com.example.Gate_pass_system.DTO;

import java.time.LocalDate;
import java.util.List;

public class CreateGatePassRequestDTO {
    private String senderServiceNumber;
    private String receiverServiceNumber;
    private String executiveOfficerServiceNumber;
    private Long outLocationId;
    private Long inLocationId;
    private String transportMode; // "BY_HAND" or "BY_VEHICLE"
    private Boolean isSlt; // null for BY_VEHICLE
    private String serviceNumber; // For SLT
    private String transporterName; // For Non-SLT or BY_VEHICLE
    private String phoneNumber; // For Non-SLT or BY_VEHICLE
    private String nic; // For Non-SLT
    private String email; // For Non-SLT
    private String vehicleNumber;
    private String vehicleModel;
    private List<ItemDTO> items;

    // Getters and Setters
    public String getSenderServiceNumber() {
        return senderServiceNumber;
    }

    public void setSenderServiceNumber(String senderServiceNumber) {
        this.senderServiceNumber = senderServiceNumber;
    }

    public String getReceiverServiceNumber() {
        return receiverServiceNumber;
    }

    public void setReceiverServiceNumber(String receiverServiceNumber) {
        this.receiverServiceNumber = receiverServiceNumber;
    }

    public String getExecutiveOfficerServiceNumber() {
        return executiveOfficerServiceNumber;
    }

    public void setExecutiveOfficerServiceNumber(String executiveOfficerServiceNumber) {
        this.executiveOfficerServiceNumber = executiveOfficerServiceNumber;
    }

    public Long getOutLocationId() {
        return outLocationId;
    }

    public void setOutLocationId(Long outLocationId) {
        this.outLocationId = outLocationId;
    }

    public Long getInLocationId() {
        return inLocationId;
    }

    public void setInLocationId(Long inLocationId) {
        this.inLocationId = inLocationId;
    }

    public String getTransportMode() {
        return transportMode;
    }

    public void setTransportMode(String transportMode) {
        this.transportMode = transportMode;
    }

    public Boolean getIsSlt() {
        return isSlt;
    }

    public void setIsSlt(Boolean isSlt) {
        this.isSlt = isSlt;
    }

    public String getServiceNumber() {
        return serviceNumber;
    }

    public void setServiceNumber(String serviceNumber) {
        this.serviceNumber = serviceNumber;
    }

    public String getTransporterName() {
        return transporterName;
    }

    public void setTransporterName(String transporterName) {
        this.transporterName = transporterName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public List<ItemDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemDTO> items) {
        this.items = items;
    }
}