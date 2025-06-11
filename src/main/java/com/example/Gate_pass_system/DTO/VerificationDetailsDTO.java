package com.example.Gate_pass_system.DTO;

import java.time.LocalDateTime;

public class VerificationDetailsDTO {
    private VerificationUserDTO executiveOfficer;
    private String executiveStatus;
    private String executiveComments;
    private LocalDateTime executiveDate;

    private VerificationUserDTO dutyOfficer;
    private String dutyStatus;
    private String dutyComments;
    private LocalDateTime dutyDate;

    public VerificationUserDTO getExecutiveOfficer() {
        return executiveOfficer;
    }

    public void setExecutiveOfficer(VerificationUserDTO executiveOfficer) {
        this.executiveOfficer = executiveOfficer;
    }

    public String getExecutiveStatus() {
        return executiveStatus;
    }

    public void setExecutiveStatus(String executiveStatus) {
        this.executiveStatus = executiveStatus;
    }

    public String getExecutiveComments() {
        return executiveComments;
    }

    public void setExecutiveComments(String executiveComments) {
        this.executiveComments = executiveComments;
    }

    public LocalDateTime getExecutiveDate() {
        return executiveDate;
    }

    public void setExecutiveDate(LocalDateTime executiveDate) {
        this.executiveDate = executiveDate;
    }

    public VerificationUserDTO getDutyOfficer() {
        return dutyOfficer;
    }

    public void setDutyOfficer(VerificationUserDTO dutyOfficer) {
        this.dutyOfficer = dutyOfficer;
    }

    public String getDutyStatus() {
        return dutyStatus;
    }

    public void setDutyStatus(String dutyStatus) {
        this.dutyStatus = dutyStatus;
    }

    public String getDutyComments() {
        return dutyComments;
    }

    public void setDutyComments(String dutyComments) {
        this.dutyComments = dutyComments;
    }

    public LocalDateTime getDutyDate() {
        return dutyDate;
    }

    public void setDutyDate(LocalDateTime dutyDate) {
        this.dutyDate = dutyDate;
    }
}