package com.example.Gate_pass_system.DTO;


import com.example.Gate_pass_system.entity.EmploymentType;

public class LoginRequestDTO {
    private String serviceNumber;
    private String password;
    private String employmentType;  // Store as String

    // Constructors
    public LoginRequestDTO() {}

    public LoginRequestDTO(String serviceNumber, String password, String employmentType) {
        this.serviceNumber = serviceNumber;
        this.password = password;
        this.employmentType = employmentType;
    }

    // Getters and Setters (String-based)
    public String getServiceNumber() {
        return serviceNumber;
    }

    public void setServiceNumber(String serviceNumber) {
        this.serviceNumber = serviceNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    // Helper method to convert String to EmploymentType
    public EmploymentType getEmploymentTypeAsEnum() {
        try {
            return EmploymentType.valueOf(employmentType.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            return null;  // Handle invalid enum values
        }
    }
}