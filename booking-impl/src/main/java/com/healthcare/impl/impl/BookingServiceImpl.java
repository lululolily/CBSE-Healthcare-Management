package com.healthcare.impl.impl;

import com.healthcare.api.model.Appointment;
import com.healthcare.api.model.AppointmentStatus;
import com.healthcare.api.service.BookingService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class BookingServiceImpl implements BookingService {
    private final List<Appointment> appointments = new ArrayList<>();
    
    public BookingServiceImpl() {
        Appointment tomorrowAppointment = new Appointment(
            UUID.randomUUID().toString(),
            "Dr. Ali Akar",
            "Jenuine Lee",
            "I am sick",
            "sick",
            "2024-12-19 11:00"
        );
        
        // Add to the appointments list
        appointments.add(tomorrowAppointment);
    }

    @Override
    public void bookAppointment(Appointment appointment) {
        appointments.add(appointment);
        System.out.println("\n=== Appointment Booked ===");
        System.out.println("Appointment ID: " + appointment.getId());
        System.out.println("Doctor ID: " + appointment.getDoctorId());
        System.out.println("Patient ID: " + appointment.getPatientId());
        System.out.println("Patient Note: " + appointment.getPatientNotes());
        System.out.println("Reason: " + appointment.getReason());
        System.out.println("Status: " + appointment.getStatus());
        System.out.println("Appointment Time: " + appointment.getDateTime());
        System.out.println("Reminder Sent: " + (appointment.isReminderSent() ? "Yes" : "No"));
        System.out.println("==========================\n");
    }

    @Override
    public void cancelAppointment(String appointmentId) {
        appointments.removeIf(a -> a.getId().equals(appointmentId));
        System.out.println("\n=== Appointment Cancelled ===");
        System.out.println("Appointment ID: " + appointmentId);
        System.out.println("=============================\n");
    }

    @Override
    public void rescheduleAppointment(String appointmentId, String newDateTime) {
        appointments.stream()
            .filter(a -> a.getId().equals(appointmentId))
            .findFirst()
            .ifPresent(a -> {
                a.setDateTime(newDateTime);
                a.setStatus(AppointmentStatus.RESCHEDULED);
                System.out.println("\n=== Appointment Rescheduled ===");
                System.out.println("Appointment ID: " + appointmentId);
                System.out.println("New Appointment Time: " + newDateTime);
                System.out.println("Status: " + AppointmentStatus.RESCHEDULED);
                System.out.println("===============================\n");
            });
    }

    @Override
    public List<Appointment> viewAppointments() {
        return appointments;
    }

    @Override
    public void acceptAppointment(String appointmentId) {
        // Find the appointment by ID and update its status to ACCEPTED
        appointments.stream()
            .filter(a -> a.getId().equals(appointmentId))
            .findFirst()
            .ifPresent(a -> {
                a.setStatus(AppointmentStatus.ACCEPTED);
                System.out.println("\n=== Appointment Accepted ===");
                System.out.println("Appointment ID: " + appointmentId);
                System.out.println("Status: " + AppointmentStatus.ACCEPTED);
                System.out.println("=============================\n");
            });
    }

    @Override
    public void rejectAppointment(String appointmentId) {
        // Find the appointment by ID and update its status to REJECTED
        appointments.stream()
            .filter(a -> a.getId().equals(appointmentId))
            .findFirst()
            .ifPresent(a -> {
                a.setStatus(AppointmentStatus.REJECTED);
                System.out.println("\n=== Appointment Rejected ===");
                System.out.println("Appointment ID: " + appointmentId);
                System.out.println("Status: " + AppointmentStatus.REJECTED);
                System.out.println("=============================\n");
            });
    }

    @Override
    public void setAvailability(String doctorId, String unavailabilityFrom, String unavailabilityTo) {
        // Logic to set doctor's unavailability during the specified time range
        System.out.println("\n=== Doctor Availability Set ===");
        System.out.println("Doctor ID: " + doctorId);
        System.out.println("Unavailable from: " + unavailabilityFrom);
        System.out.println("Unavailable to: " + unavailabilityTo);
        System.out.println("==============================\n");
    }
    
    @Override
    public void sendReminder(String appointmentId) {
        Appointment appointment = findAppointmentById(appointmentId);
        if (appointment == null) {
            System.out.println("Appointment not found.");
            return;
        }

        // Check if the appointment is within 24 hours from now
        LocalDateTime appointmentTime = LocalDateTime.parse(appointment.getDateTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalDateTime now = LocalDateTime.now();

        if (appointmentTime.isAfter(now) && appointmentTime.isBefore(now.plusHours(24))) {
            System.out.println("Reminder: Your appointment with " + appointment.getDoctorId() + " is scheduled for tomorrow at " + appointment.getDateTime());
            appointment.setReminderSent(true);
        } else {
        }
    }

    // Helper method to find an appointment by ID
    private Appointment findAppointmentById(String appointmentId) {
        return appointments.stream()
            .filter(appt -> appt.getId().equals(appointmentId))
            .findFirst()
            .orElse(null);
    }
}

