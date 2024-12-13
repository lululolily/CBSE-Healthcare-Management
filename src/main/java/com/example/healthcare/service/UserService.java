package com.example.healthcare.service;

import com.example.healthcare.model.*;
import com.example.healthcare.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(String username, String password, String role, String firstName, String lastName, String contactNumber, String specialization, String email, String workingHoursStart, String workingHoursEnd) {
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username already exists");
        }

        // Create and save the User entity
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role.equalsIgnoreCase("doctor") ? Role.DOCTOR : Role.PATIENT);
        userRepository.save(user);

        if (role.equalsIgnoreCase("doctor")) {
            // Validate and parse working hours
            LocalTime startTime = null;
            LocalTime endTime = null;

            try {
                if (workingHoursStart != null && workingHoursEnd != null) {
                    startTime = LocalTime.parse(workingHoursStart); // Parse start time
                    endTime = LocalTime.parse(workingHoursEnd);     // Parse end time
                }
            } catch (DateTimeParseException e) {
                throw new RuntimeException("Invalid working hours format. Please use HH:mm.");
            }

            // Create and save the Doctor entity
            Doctor doctor = new Doctor();
            doctor.setUser(user);
            doctor.setFirstName(firstName);
            doctor.setLastName(lastName);
            doctor.setContactNumber(contactNumber);
            doctor.setSpecialization(specialization);
            doctor.setWorkingHoursStart(startTime); // Set working hours
            doctor.setWorkingHoursEnd(endTime);
            doctorRepository.save(doctor);
        } else if (role.equalsIgnoreCase("patient")) {
            // Create and save the Patient entity
            Patient patient = new Patient();
            patient.setUser(user);
            patient.setFirstName(firstName);
            patient.setLastName(lastName);
            patient.setContactNumber(contactNumber);
            patient.setEmail(email);
            patientRepository.save(patient);
        }

        return user;
    }

    public User authenticateUser(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid username or password");
        }

        return user;
    }
}
