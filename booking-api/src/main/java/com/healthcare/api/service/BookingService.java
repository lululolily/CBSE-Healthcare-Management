package com.healthcare.api.service;

import com.healthcare.api.model.Appointment;

import java.util.List;

public interface BookingService {
    void bookAppointment(Appointment appointment);
    void cancelAppointment(String appointmentId);
    void rescheduleAppointment(String appointmentId, String newDateTime);
    List<Appointment> viewAppointments();
    void acceptAppointment(String appointmentId);
    void rejectAppointment(String appointmentId);
    void setAvailability(String doctorId, String unavailabilityFrom, String unavailabilityTo);
}
