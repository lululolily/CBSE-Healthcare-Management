package com.example.healthcare.interfaces;

import com.example.healthcare.model.Appointment;
import java.time.LocalDateTime;

public interface IManageAppointmentRequest {
    Appointment createAppointment(Long doctorId, Long patientId, LocalDateTime appointmentTime, String reason);

    Appointment acceptAppointment(Long appointmentId);

    Appointment rejectAppointment(Long appointmentId, String rejectionReason);

    void cancelAppointment(Long appointmentId);

    Appointment rescheduleAppointment(Long appointmentId, LocalDateTime newTime);
}