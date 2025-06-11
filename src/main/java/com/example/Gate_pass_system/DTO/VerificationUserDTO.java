package com.example.Gate_pass_system.DTO;


public class VerificationUserDTO {
    private String serviceNumber;
    private String name;

    public VerificationUserDTO() {
    }

    public VerificationUserDTO(String serviceNumber, String name) {
        this.serviceNumber = serviceNumber;
        this.name = name;
    }

    public String getServiceNumber() {
        return serviceNumber;
    }

    public void setServiceNumber(String serviceNumber) {
        this.serviceNumber = serviceNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}