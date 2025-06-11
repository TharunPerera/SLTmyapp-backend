package com.example.Gate_pass_system.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "transportation")
public class Transportation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transportation_id")
    private Long transportationId;

    @OneToOne
    @JoinColumn(name = "request_id", referencedColumnName = "ref_no")
    private GatePassRequest request;

    @Column(name = "transport_mode")
    private String transportMode; // "BY_HAND" or "BY_VEHICLE"

    @Column(name = "is_slt")
    private Boolean isSlt; // null for BY_VEHICLE

    @Column(name = "service_number")
    private String serviceNumber; // For SLT

    @Column(name = "transporter_name")
    private String transporterName; // For Non-SLT or BY_VEHICLE

    @Column(name = "phone_number")
    private String phoneNumber; // For Non-SLT or BY_VEHICLE

    @Column(name = "nic")
    private String nic; // For Non-SLT

    @Column(name = "email")
    private String email; // For Non-SLT

    @Column(name = "vehicle_number")
    private String vehicleNumber;

    @Column(name = "vehicle_model")
    private String vehicleModel;

    // Getters and Setters
    public Long getTransportationId() {
        return transportationId;
    }

    public void setTransportationId(Long transportationId) {
        this.transportationId = transportationId;
    }

    public GatePassRequest getRequest() {
        return request;
    }

    public void setRequest(GatePassRequest request) {
        this.request = request;
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
}