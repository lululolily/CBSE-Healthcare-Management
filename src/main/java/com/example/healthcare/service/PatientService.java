package com.example.healthcare.service;

import com.example.healthcare.dto.PatientDTO;
import com.example.healthcare.model.Patient;
import com.example.healthcare.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public Patient getPatientById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
    }

    public void updatePatientProfile(Long id, PatientDTO patientDTO) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        patient.setFirstName(patientDTO.getFirstName());
        patient.setLastName(patientDTO.getLastName());
        patient.setContactNumber(patientDTO.getContactNumber());
        patient.setEmail(patientDTO.getEmail());
        patientRepository.save(patient);
    }
}
