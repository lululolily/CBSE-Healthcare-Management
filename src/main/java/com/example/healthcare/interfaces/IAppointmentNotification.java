package com.example.healthcare.interfaces;

import java.time.LocalDateTime;

public interface IAppointmentNotification {
    void sendAppointmentAcceptedNotification(String email, String doctorName, LocalDateTime appointmentTime);

    void sendAppointmentRejectionNotification(String email, String doctorName, String reason);

    void sendAppointmentReminder(String email, String doctorName, LocalDateTime appointmentTime);
}
