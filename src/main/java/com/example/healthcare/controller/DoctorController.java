package com.example.healthcare.controller;

import com.example.healthcare.dto.DoctorDTO;
import com.example.healthcare.model.Doctor;
import com.example.healthcare.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDTO> getDoctorById(@PathVariable Long id) {
        Doctor doctor = doctorService.getDoctorById(id);

        DoctorDTO response = new DoctorDTO();
        response.setId(doctor.getId());
        response.setFirstName(doctor.getFirstName());
        response.setLastName(doctor.getLastName());
        response.setSpecialization(doctor.getSpecialization());
        response.setContactNumber(doctor.getContactNumber());
        response.setWorkingHoursStart(doctor.getWorkingHoursStart());
        response.setWorkingHoursEnd(doctor.getWorkingHoursEnd());

        return ResponseEntity.ok(response);
    }


    @PutMapping("/{id}")
    public ResponseEntity<String> updateDoctor(@PathVariable Long id, @RequestBody DoctorDTO doctorDTO) {
        doctorService.updateDoctorProfile(id, doctorDTO);
        return ResponseEntity.ok("Doctor profile updated successfully");
    }

    @GetMapping("/specialization/{specialization}")
    public ResponseEntity<List<DoctorDTO>> getDoctorsBySpecialization(@PathVariable String specialization) {
        List<Doctor> doctors = doctorService.getDoctorsBySpecialization(specialization);

        List<DoctorDTO> response = doctors.stream().map(doctor -> {
            DoctorDTO dto = new DoctorDTO();
            dto.setId(doctor.getId());
            dto.setFirstName(doctor.getFirstName());
            dto.setLastName(doctor.getLastName());
            dto.setSpecialization(doctor.getSpecialization());
            dto.setContactNumber(doctor.getContactNumber());
            dto.setWorkingHoursStart(doctor.getWorkingHoursStart());
            dto.setWorkingHoursEnd(doctor.getWorkingHoursEnd());
            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{id}/working-hours")
    public ResponseEntity<String> updateWorkingHours(@PathVariable Long id, @RequestBody DoctorDTO doctorDTO) {
        doctorService.updateDoctorProfile(id, doctorDTO);
        return ResponseEntity.ok("Doctor working hours updated successfully");
    }

}
