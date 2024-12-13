package com.example.healthcare.service;

import com.example.healthcare.model.Appointment;
import com.example.healthcare.model.Doctor;
import com.example.healthcare.model.Patient;
import com.example.healthcare.repository.AppointmentRepository;
import com.example.healthcare.repository.DoctorRepository;
import com.example.healthcare.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    public Appointment createAppointment(Long doctorId, Long patientId, LocalDateTime appointmentStartTime, LocalDateTime appointmentEndTime, String reason) {
        // Fetch doctor and patient
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        // Validate doctor's working hours
        LocalTime workingStart = doctor.getWorkingHoursStart();
        LocalTime workingEnd = doctor.getWorkingHoursEnd();
        LocalTime appointmentStart = appointmentStartTime.toLocalTime();
        LocalTime appointmentEnd = appointmentEndTime.toLocalTime();

        if (appointmentStart.isBefore(workingStart) || appointmentEnd.isAfter(workingEnd)) {
            throw new RuntimeException("Appointment time is outside the doctor's working hours");
        }

        // Validate overlapping appointments
        boolean overlaps = appointmentRepository.existsByDoctorAndTimeRange(doctorId, appointmentStartTime, appointmentEndTime);
        if (overlaps) {
            throw new RuntimeException("Appointment time overlaps with another appointment");
        }

        // Create appointment
        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setAppointmentStartTime(appointmentStartTime);
        appointment.setAppointmentEndTime(appointmentEndTime);
        appointment.setReason(reason);
        appointment.setStatus("PENDING");

        return appointmentRepository.save(appointment);
    }
    
    public Appointment acceptAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        if (!"PENDING".equals(appointment.getStatus())) {
            throw new RuntimeException("Only pending appointments can be accepted");
        }

        appointment.setStatus("ACCEPTED");
        return appointmentRepository.save(appointment);
    }

    public void cancelAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        appointment.setStatus("CANCELLED");
        
        if ("CANCELLED".equals(appointment.getStatus())) {
        	throw new RuntimeException("Appointment has already been cancelled");
        }
        
        appointmentRepository.save(appointment);
    }

    public List<Appointment> getAppointmentsByPatient(Long patientId) {
        return appointmentRepository.findByPatientIdOrderByAppointmentStartTimeAsc(patientId);
    }

    public List<Appointment> getAppointmentsByDoctor(Long doctorId) {
        return appointmentRepository.findByDoctorIdOrderByAppointmentStartTimeAsc(doctorId);
    }

    public Appointment rescheduleAppointment(Long appointmentId, LocalDateTime newStartTime, LocalDateTime newEndTime) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        if ("CANCELLED".equals(appointment.getStatus())) {
            throw new RuntimeException("Cannot reschedule a canceled appointment");
        }

                boolean overlaps = appointmentRepository.existsByDoctorAndTimeRange(
            appointment.getDoctor().getId(), newStartTime, newEndTime
        );
        if (overlaps) {
            throw new RuntimeException("New slot is not available");
        }

        appointment.setAppointmentStartTime(newStartTime);
        appointment.setAppointmentEndTime(newEndTime);
        appointment.setStatus("RESCHEDULED");

        return appointmentRepository.save(appointment);
    }
}
