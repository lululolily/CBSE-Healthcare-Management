package com.example.healthcare.interfaces;

import com.example.healthcare.model.Appointment;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface IManageAppointmentRequest {
    Appointment createAppointment(Long doctorId, Long patientId, LocalDateTime appointmentTime, String reason);

    void updateAppointment(Appointment appointment);

    Appointment acceptAppointment(Long appointmentId);

    Appointment rejectAppointment(Long appointmentId, String rejectionReason);

    void cancelAppointment(Long appointmentId);

    Appointment rescheduleAppointment(Long appointmentId, LocalDateTime newTime);
    
    List<LocalDateTime> getAvailableSlots(Long doctorId, LocalDate date);

    // for notification
    List<Appointment> getAppointmentsForReminders(LocalDateTime now, LocalDateTime reminderTime);
}