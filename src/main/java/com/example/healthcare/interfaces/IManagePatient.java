package com.example.healthcare.interfaces;

import com.example.healthcare.dto.PatientDTO;
import com.example.healthcare.model.Patient;

public interface IManagePatient {
    Patient getPatientById(Long id);

    void updatePatientProfile(Long id, PatientDTO patientDTO);
}
