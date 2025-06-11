package com.example.Gate_pass_system.controller;

import com.example.Gate_pass_system.DTO.LoginRequestDTO;
import com.example.Gate_pass_system.entity.User;
import com.example.Gate_pass_system.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequest) {
        User authenticatedUser = loginService.authenticateUser(loginRequest);

        if (authenticatedUser != null) {
            // Create a response DTO to return user details without password
            return ResponseEntity.ok(new UserResponseDTO(
                    authenticatedUser.getServiceNumber(),
                    authenticatedUser.getRole().name(),
                    authenticatedUser.getFirstName(),
                    authenticatedUser.getLastName(),
                    authenticatedUser.getEmploymentType().name()

            ));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid credentials or employment type");
        }
    }

    // Inner DTO to safely return user details
    private static class UserResponseDTO {
        private String serviceNumber;
        private String role;
        private String firstName;
        private String lastName;
        private String employmentType;

        public UserResponseDTO(String serviceNumber, String role, String firstName,
                               String lastName, String employmentType) {
            this.serviceNumber = serviceNumber;
            this.role = role;
            this.firstName = firstName;
            this.lastName = lastName;
            this.employmentType = employmentType;
        }

        // Getters
        public String getServiceNumber() { return serviceNumber; }
        public String getRole() { return role; }
        public String getFirstName() { return firstName; }
        public String getLastName() { return lastName; }
        public String getEmploymentType() { return employmentType; }
    }
}