package com.example.Gate_pass_system.DTO;


// User DTO for receipt details
public class ReceiptUserDTO {
    private String serviceNumber;
    private String name;

    public ReceiptUserDTO(String serviceNumber, String name) {
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