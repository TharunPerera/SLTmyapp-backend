//package com.example.Gate_pass_system.DTO;
//
//public class UserDTO {
//    private String serviceNumber;
//    private String firstName;
//    private String lastName;
//    private String email;
//    private String password;
//    private String role;
//    private String designation;
//
//    public String getServiceNumber() {
//        return serviceNumber;
//    }
//
//    public void setServiceNumber(String serviceNumber) {
//        this.serviceNumber = serviceNumber;
//    }
//
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public String getRole() {
//        return role;
//    }
//
//    public void setRole(String role) {
//        this.role = role;
//    }
//
//    public String getDesignation() {
//        return designation;
//    }
//
//    public void setDesignation(String designation) {
//        this.designation = designation;
//    }
//
//    public String getSection() {
//        return section;
//    }
//
//    public void setSection(String section) {
//        this.section = section;
//    }
//
//    public String getGroup() {
//        return group;
//    }
//
//    public void setGroup(String group) {
//        this.group = group;
//    }
//
//    public String getContactNumber() {
//        return contactNumber;
//    }
//
//    public void setContactNumber(String contactNumber) {
//        this.contactNumber = contactNumber;
//    }
//
//    public String getEmployment() {
//        return employment;
//    }
//
//    public void setEmployment(String employment) {
//        this.employment = employment;
//    }
//
//    public String getBranch() {
//        return branch;
//    }
//
//    public void setBranch(String branch) {
//        this.branch = branch;
//    }
//
//    private String section;
//    private String group;
//    private String contactNumber;
//    private String employment;
//    private String branch;
//}


package com.example.Gate_pass_system.DTO;

public class UserDTO {
    private String serviceNumber;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role; // Accepts "EMPLOYEE", "EXECUTIVE", "DUTY_OFFICER"
    private String employmentType; // Accepts "SLT", "NON_SLT"
    private String branch;
    private String designation;
    private String section;
    private String group;
    private String contactNumber;

    // Getters and Setters
    public String getServiceNumber() {
        return serviceNumber;
    }

    public void setServiceNumber(String serviceNumber) {
        this.serviceNumber = serviceNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}