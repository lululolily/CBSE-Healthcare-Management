package com.example.healthcare.controller;

import com.example.healthcare.dto.LoginRequestDTO;
import com.example.healthcare.dto.RegisterRequestDTO;
import com.example.healthcare.model.User;
import com.example.healthcare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequestDTO request) {
        userService.registerUser(
            request.getUsername(),
            request.getPassword(),
            request.getRole(),
            request.getFirstName(),
            request.getLastName(),
            request.getContactNumber(),
            request.getSpecialization(),
            request.getEmail(),
            request.getWorkingHoursStart(), 
            request.getWorkingHoursEnd()    
        );
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequestDTO request) {
        User user = userService.authenticateUser(request.getUsername(), request.getPassword());
        return ResponseEntity.ok("Login successful for user: " + user.getUsername());
    }
}
