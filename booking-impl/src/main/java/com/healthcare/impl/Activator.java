package com.healthcare.impl;

import com.healthcare.api.model.Appointment;
import com.healthcare.api.model.AppointmentStatus;
import com.healthcare.api.service.BookingService;
import com.healthcare.impl.impl.BookingServiceImpl;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import java.util.List;
import java.util.UUID;

public class Activator implements BundleActivator {

    private ServiceRegistration<BookingService> registration;

    // Static flag to toggle between patient and doctor roles
    private static boolean runDoctorRoleTest = false;

    @Override
    public void start(BundleContext context) {
        System.out.println("Starting Booking Service Bundle...");
        
        // Initialize the Booking Service
        BookingService bookingService = new BookingServiceImpl();
        registration = context.registerService(BookingService.class, bookingService, null);
        System.out.println("Booking Service Registered!");

        if (!runDoctorRoleTest) {
            // Run patient role test
            System.out.println("Running test for Patient role...");
            checkAndSendReminders(bookingService);
            runPatientRoleTest(bookingService);
        } else {
            // Run doctor role test
            System.out.println("Running test for Doctor role...");
            runDoctorRoleTest(bookingService);
        }

        // Toggle the role for the next bundle start
        runDoctorRoleTest = !runDoctorRoleTest;
    }

    @Override
    public void stop(BundleContext context) {
        System.out.println("Stopping Booking Service Bundle...");
        if (registration != null) {
            registration.unregister();
            System.out.println("Booking Service Unregistered.");
        }
    }
    
    private void checkAndSendReminders(BookingService bookingService) {
        List<Appointment> appointments = bookingService.viewAppointments();

        // Loop through all appointments and send reminders if within 24 hours
        for (Appointment appointment : appointments) {
            bookingService.sendReminder(appointment.getId());
        }
    }

    // Method to test the patient role
    private void runPatientRoleTest(BookingService bookingService) {
        // Hardcoded Appointment Data for Patient Role
        Appointment appointment1 = new Appointment(
            UUID.randomUUID().toString(),  // id
            "Dr. Colin",                   // doctorId
            "Jakarta Java",                  // patientId
            "I am sick, I feel very bad",             // patientNotes
            "illness",                    // reason
            "2024-12-20 10:00"             // dateTime
        );

        Appointment appointment2 = new Appointment(
            UUID.randomUUID().toString(),  // id
            "Dr. Daniel",                   // doctorId
            "Mary Jetty",                  // patientId
            "No notes",             // patientNotes
            "Headache",                    // reason
            "2024-12-21 11:00"             // dateTime
        );

        // Book Appointments
        bookingService.bookAppointment(appointment1);
        bookingService.bookAppointment(appointment2);

        // View Appointments
        System.out.println("\n=== Current Appointments ===");
        List<Appointment> currentAppointments = bookingService.viewAppointments();
        if (currentAppointments.isEmpty()) {
            System.out.println("No appointments available.\n");
        } else {
            currentAppointments.forEach(appointment -> {
                System.out.println("Appointment ID: " + appointment.getId());
                System.out.println("Doctor ID: " + appointment.getDoctorId());
                System.out.println("Patient ID: " + appointment.getPatientId());
                System.out.println("Patient Notes: " + appointment.getPatientNotes());
                System.out.println("Reason: " + appointment.getReason());
                System.out.println("Status: " + appointment.getStatus());
                System.out.println("Appointment Time: " + appointment.getDateTime());
                System.out.println("Reminder Sent: " + (appointment.isReminderSent() ? "Yes" : "No"));
                System.out.println("============================\n");
            });

        // Cancel the first appointment
        bookingService.cancelAppointment(appointment1.getId());

        // Reschedule the second appointment
        bookingService.rescheduleAppointment(appointment2.getId(), "2024-12-22 15:00");

        // View Appointments after changes
        System.out.println("\n=== Updated Appointments ===");
        if (currentAppointments.isEmpty()) {
            System.out.println("No appointments available.\n");
        } else {
            currentAppointments.forEach(appointment -> {
                System.out.println("Appointment ID: " + appointment.getId());
                System.out.println("Doctor ID: " + appointment.getDoctorId());
                System.out.println("Patient ID: " + appointment.getPatientId());
                System.out.println("Patient Notes: " + appointment.getPatientNotes());
                System.out.println("Reason: " + appointment.getReason());
                System.out.println("Status: " + appointment.getStatus());
                System.out.println("Appointment Time: " + appointment.getDateTime());
                System.out.println("Reminder Sent: " + (appointment.isReminderSent() ? "Yes" : "No"));
                System.out.println("============================\n");
            });
        }
        }
    }

    // Method to test the doctor role
    private void runDoctorRoleTest(BookingService bookingService) {
        Appointment appointment1 = new Appointment(
                UUID.randomUUID().toString(),  // id
                "Dr. Ali Akar",                   // doctorId
                "Abu Masa",                  // patientId
                "can you suggest me some way to cure my cold",             // patientNotes
                "sick",                    // reason
                "2024-12-23 13:00"             // dateTime
            );

        Appointment appointment2 = new Appointment(
            UUID.randomUUID().toString(),  // id
            "Dr. Ali Akar",                   // doctorId
            "Bobby Nutella",                  // patientId
            "no note",             // patientNotes
            "Serious Flu",                    // reason
            "2024-12-24 14:00"             // dateTime
        );
        
        bookingService.bookAppointment(appointment1);
        bookingService.bookAppointment(appointment2);
        
        // Accept the first appointment
        bookingService.acceptAppointment(appointment1.getId());

        // Reject the second appointment
        bookingService.rejectAppointment(appointment2.getId());

        // Set Doctor's availability (Doctor Ali Akar is unavailable from 2024-12-22 09:00 to 2024-12-23 12:00)
        String unavailabilityFrom = "2024-12-22 09:00";
        String unavailabilityTo = "2024-12-23 12:00";
        
        // Set availability
        bookingService.setAvailability("Dr. Ali Akar", unavailabilityFrom, unavailabilityTo);
    }
}

