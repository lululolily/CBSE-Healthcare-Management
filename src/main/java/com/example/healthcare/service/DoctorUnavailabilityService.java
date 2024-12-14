package com.example.healthcare.service;

import com.example.healthcare.interfaces.IManageAvailability;

import com.example.healthcare.model.Appointment;
import com.example.healthcare.model.Doctor;
import com.example.healthcare.model.DoctorUnavailability;

import com.example.healthcare.repository.AppointmentRepository;
import com.example.healthcare.repository.DoctorRepository;
import com.example.healthcare.repository.DoctorUnavailabilityRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import java.util.List;

@Service
public class DoctorUnavailabilityService implements IManageAvailability  {

    @Autowired
    private DoctorUnavailabilityRepository unavailabilityRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    public void addUnavailability(Long doctorId, LocalDateTime from, LocalDateTime to) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        List<Appointment> overlappingAppointments = appointmentRepository.findByDoctorIdAndAppointmentTimeBetween(
                doctorId, from, to);
        if (!overlappingAppointments.isEmpty()) {
            throw new RuntimeException("Cannot set unavailability. There are existing appointments in the specified time range.");
        }

        DoctorUnavailability unavailability = new DoctorUnavailability();
        unavailability.setDoctor(doctor);
        unavailability.setUnavailableFrom(from);
        unavailability.setUnavailableTo(to);

        unavailabilityRepository.save(unavailability);
    }
}
