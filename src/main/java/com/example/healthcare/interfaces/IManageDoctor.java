package com.example.healthcare.interfaces;

import com.example.healthcare.dto.DoctorDTO;
import com.example.healthcare.model.Doctor;
import java.util.List;

public interface IManageDoctor {
    Doctor getDoctorById(Long id);

    void updateDoctorProfile(Long id, DoctorDTO doctorDTO);

    List<Doctor> getDoctorsBySpecialization(String specialization);
}
