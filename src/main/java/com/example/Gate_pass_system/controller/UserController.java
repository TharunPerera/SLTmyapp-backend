package com.example.Gate_pass_system.controller;

import com.example.Gate_pass_system.DTO.UserDTO;
import com.example.Gate_pass_system.entity.User;
import com.example.Gate_pass_system.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserDTO userDTO) {
        try {
            User createdUser = userService.createUser(userDTO);
            return ResponseEntity.ok("User created successfully: " + createdUser.getServiceNumber());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/{serviceNumber}")
    public ResponseEntity<String> updateUser(@PathVariable String serviceNumber, @RequestBody UserDTO userDTO) {
        try {
            User updatedUser = userService.updateUser(serviceNumber, userDTO);
            return ResponseEntity.ok("User updated successfully: " + updatedUser.getServiceNumber());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/{serviceNumber}")
    public ResponseEntity<String> deleteUser(@PathVariable String serviceNumber) {
        try {
            boolean deleted = userService.deleteUser(serviceNumber);
            if (deleted) {
                return ResponseEntity.ok("User deleted successfully: " + serviceNumber);
            } else {
                return ResponseEntity.badRequest().body("User not found: " + serviceNumber);
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/{serviceNumber}")
    public ResponseEntity<?> getUserByServiceNumber(@PathVariable String serviceNumber) {
        try {
            User user = userService.getUserByServiceNumber(serviceNumber);
            if (user == null) {
                return ResponseEntity.badRequest().body("User not found: " + serviceNumber);
            }
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/executives")
    public ResponseEntity<?> getAllExecutiveRoleUsers() {
        try {
            return ResponseEntity.ok(userService.getAllExecutiveRoleUsers());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/employees")
    public ResponseEntity<?> getAllEmployeeRoleUsers() {
        try {
            return ResponseEntity.ok(userService.getAllEmployeeRoleUsers());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}