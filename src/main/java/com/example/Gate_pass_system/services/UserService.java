//package com.example.Gate_pass_system.services;
//
//import com.example.Gate_pass_system.DTO.UserDTO;
//import com.example.Gate_pass_system.entity.User;
//import com.example.Gate_pass_system.repo.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//
//import java.time.LocalDateTime;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class UserService {
//    @Autowired
//    private UserRepository userRepository;
//
//    // Create a new user
//    public User createUser(User user) {
//        // Set created at timestamp
//        user.setCreatedAt(LocalDateTime.now());
//        return userRepository.save(user);
//    }
//
//    // Update an existing user
//    public User updateUser(String serviceNumber, User userDetails) {
//        User existingUser = userRepository.findByServiceNumber(serviceNumber);
//
//        if (existingUser != null) {
//            // Update fields
//            existingUser.setFirstName(userDetails.getFirstName());
//            existingUser.setLastName(userDetails.getLastName());
//            existingUser.setEmail(userDetails.getEmail());
//            existingUser.setRole(userDetails.getRole());
//            existingUser.setDesignation(userDetails.getDesignation());
//            existingUser.setSection(userDetails.getSection());
//            existingUser.setGroup(userDetails.getGroup());
//            existingUser.setContactNumber(userDetails.getContactNumber());
//            existingUser.setEmployment(userDetails.getEmployment());
//            existingUser.setBranch(userDetails.getBranch());
//
//            return userRepository.save(existingUser);
//        }
//
//        return null;
//    }
//
//    // Delete user by service number
//    public boolean deleteUser(String serviceNumber) {
//        User existingUser = userRepository.findByServiceNumber(serviceNumber);
//
//        if (existingUser != null) {
//            userRepository.delete(existingUser);
//            return true;
//        }
//
//        return false;
//    }
//
//    public List<Map<String, String>> getAllExecutiveRoleUsers() {
//        List<Object[]> executiveUsers = userRepository.findAllExecutiveRoleUsers();
//
//        return executiveUsers.stream()
//                .map(user -> {
//                    Map<String, String> executive = new HashMap<>();
//                    executive.put("serviceNumber", (String) user[0]); // Assuming service number is the first element
//                    executive.put("fullName", user[1] + " " + user[2]); // Assuming firstName and lastName are next
//                    return executive;
//                })
//                .collect(Collectors.toList());
//    }
//
//
//    // New method to get user by service number
//    public User getUserByServiceNumber(String serviceNumber) {
//        return userRepository.findByServiceNumber(serviceNumber);
//    }
//}

package com.example.Gate_pass_system.services;

import com.example.Gate_pass_system.DTO.UserDTO;
import com.example.Gate_pass_system.entity.EmployeeRole;
import com.example.Gate_pass_system.entity.EmploymentType;
import com.example.Gate_pass_system.entity.User;
import com.example.Gate_pass_system.repo.LocationRepository;
import com.example.Gate_pass_system.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LocationRepository locationRepository;

    // Create a new user
    public User createUser(UserDTO userDTO) {
        // Validate branch exists in locations table
        if (!locationRepository.existsByLocationName(userDTO.getBranch())) {
            throw new RuntimeException("Invalid branch: " + userDTO.getBranch());
        }

        // Validate role
        EmployeeRole role;
        try {
            role = EmployeeRole.valueOf(userDTO.getRole());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid role: " + userDTO.getRole());
        }

        // Validate employment type
        EmploymentType employmentType;
        try {
            employmentType = EmploymentType.valueOf(userDTO.getEmploymentType());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid employment type: " + userDTO.getEmploymentType());
        }

        // Check if service number or email already exists
        if (userRepository.existsById(userDTO.getServiceNumber())) {
            throw new RuntimeException("Service number already exists: " + userDTO.getServiceNumber());
        }
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new RuntimeException("Email already exists: " + userDTO.getEmail());
        }

        User user = new User();
        user.setServiceNumber(userDTO.getServiceNumber());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword()); // Consider hashing in production
        user.setRole(role);
        user.setEmploymentType(employmentType);
        user.setBranch(userDTO.getBranch());
        user.setDesignation(userDTO.getDesignation());
        user.setSection(userDTO.getSection());
        user.setGroup(userDTO.getGroup());
        user.setContactNumber(userDTO.getContactNumber());

        return userRepository.save(user);
    }

    // Update an existing user
    public User updateUser(String serviceNumber, UserDTO userDTO) {
        User existingUser = userRepository.findByServiceNumber(serviceNumber);
        if (existingUser == null) {
            throw new RuntimeException("User not found with service number: " + serviceNumber);
        }

        // Validate branch
        if (!locationRepository.existsByLocationName(userDTO.getBranch())) {
            throw new RuntimeException("Invalid branch: " + userDTO.getBranch());
        }

        // Validate role
        EmployeeRole role;
        try {
            role = EmployeeRole.valueOf(userDTO.getRole());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid role: " + userDTO.getRole());
        }

        // Validate employment type
        EmploymentType employmentType;
        try {
            employmentType = EmploymentType.valueOf(userDTO.getEmploymentType());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid employment type: " + userDTO.getEmploymentType());
        }

        // Update fields
        existingUser.setFirstName(userDTO.getFirstName());
        existingUser.setLastName(userDTO.getLastName());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setRole(role);
        existingUser.setEmploymentType(employmentType);
        existingUser.setBranch(userDTO.getBranch());
        existingUser.setDesignation(userDTO.getDesignation());
        existingUser.setSection(userDTO.getSection());
        existingUser.setGroup(userDTO.getGroup());
        existingUser.setContactNumber(userDTO.getContactNumber());

        return userRepository.save(existingUser);
    }

    // Delete user by service number
    public boolean deleteUser(String serviceNumber) {
        User existingUser = userRepository.findByServiceNumber(serviceNumber);
        if (existingUser != null) {
            userRepository.delete(existingUser);
            return true;
        }
        return false;
    }

    // Get all executive role users
    public List<Map<String, String>> getAllExecutiveRoleUsers() {
        List<Object[]> executiveUsers = userRepository.findAllExecutiveRoleUsers();
        return executiveUsers.stream()
                .map(user -> {
                    Map<String, String> executive = new HashMap<>();
                    executive.put("serviceNumber", (String) user[0]);
                    executive.put("fullName", user[1] + " " + user[2]);
                    return executive;
                })
                .collect(Collectors.toList());
    }

    // Get user by service number
    public User getUserByServiceNumber(String serviceNumber) {
        return userRepository.findByServiceNumber(serviceNumber);
    }


    public List<Map<String, String>> getAllEmployeeRoleUsers() {
        List<Object[]> employeeUsers = userRepository.findAllEmployeeRoleUsers();
        return employeeUsers.stream()
                .map(user -> {
                    Map<String, String> employee = new HashMap<>();
                    employee.put("serviceNumber", (String) user[0]);
                    employee.put("fullName", user[1] + " " + user[2]);
                    employee.put("employmentType", (String) user[3]); // Include employmentType
                    return employee;
                })
                .collect(Collectors.toList());
    }
}