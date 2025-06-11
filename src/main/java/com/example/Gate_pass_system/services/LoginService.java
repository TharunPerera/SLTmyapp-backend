package com.example.Gate_pass_system.services;

import com.example.Gate_pass_system.DTO.LoginRequestDTO;
import com.example.Gate_pass_system.entity.User;
import com.example.Gate_pass_system.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class LoginService {

    @Autowired
    private UserRepository userRepository;

    public User authenticateUser(LoginRequestDTO loginRequest) {
        // Find user by service number and employment type
        User user = userRepository.findByServiceNumberAndEmploymentType(
                loginRequest.getServiceNumber(),
                loginRequest.getEmploymentTypeAsEnum()
        );

        // Check if user exists and password matches directly
        if (user != null && user.getPassword().equals(loginRequest.getPassword())) {
            return user;
        }

        return null;
    }
}