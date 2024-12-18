package com.example.healthcare.service;

import com.example.healthcare.dto.DoctorDTO;
import com.example.healthcare.interfaces.IManageDoctor;
import com.example.healthcare.model.Doctor;
import com.example.healthcare.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorService implements IManageDoctor {

    @Autowired
    private DoctorRepository doctorRepository;

    public Doctor getDoctorById(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
    }

    public void updateDoctorProfile(Long id, DoctorDTO doctorDTO) {
    Doctor doctor = doctorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Doctor not found"));

    // Update only non-null fields from DoctorDTO
    if (doctorDTO.getFirstName() != null) {
        doctor.setFirstName(doctorDTO.getFirstName());
    }
    if (doctorDTO.getLastName() != null) {
        doctor.setLastName(doctorDTO.getLastName());
    }
    if (doctorDTO.getContactNumber() != null) {
        doctor.setContactNumber(doctorDTO.getContactNumber());
    }
    if (doctorDTO.getSpecialization() != null) {
        doctor.setSpecialization(doctorDTO.getSpecialization());
    }
    if (doctorDTO.getWorkingHoursStart() != null) {
        doctor.setWorkingHoursStart(doctorDTO.getWorkingHoursStart());
    }
    if (doctorDTO.getWorkingHoursEnd() != null) {
        doctor.setWorkingHoursEnd(doctorDTO.getWorkingHoursEnd());
    }

    doctorRepository.save(doctor);
}


    public List<Doctor> getDoctorsBySpecialization(String specialization) {
        return doctorRepository.findBySpecialization(specialization);
    }
}
