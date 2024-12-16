package com.healthcare.client;

import com.healthcare.api.model.Appointment;
import com.healthcare.api.service.BookingService;
import org.osgi.framework.*;

import java.util.List;
import java.util.UUID;

public class BookingClient {
    private BookingService bookingService;

    public BookingClient(BundleContext context) {
        // Fetch the BookingService using ServiceReference
        ServiceReference<BookingService> reference = context.getServiceReference(BookingService.class);
        if (reference != null) {
            bookingService = context.getService(reference);
        } else {
            System.out.println("BookingService not available.");
        }
    }

    public void run() {
        if (bookingService == null) {
            System.out.println("BookingService not initialized.");
            return;
        }

        // Book an appointment
        Appointment appointment1 = new Appointment(
                UUID.randomUUID().toString(),  // id
                "Doctor123",                   // doctorId
                "Patient456",                  // patientId
                "Patient notes 1",             // patientNotes
                "Reason 1",                    // reason
                "2024-12-20 10:30"             // dateTime
            );

            Appointment appointment2 = new Appointment(
                UUID.randomUUID().toString(),  // id
                "Doctor789",                   // doctorId
                "Patient123",                  // patientId
                "Patient notes 2",             // patientNotes
                "Reason 2",                    // reason
                "2024-12-21 11:00"             // dateTime
            );
        bookingService.bookAppointment(appointment1);
        bookingService.bookAppointment(appointment2);

        // View all appointments
        System.out.println("\n--- Viewing Appointments ---");
        List<Appointment> appointments = bookingService.viewAppointments();
        appointments.forEach(System.out::println);

        // Reschedule an appointment
        System.out.println("\n--- Rescheduling Appointment 1 ---");
        bookingService.rescheduleAppointment("1", "2024-06-12 09:00");

        // Cancel an appointment
        System.out.println("\n--- Cancelling Appointment 2 ---");
        bookingService.cancelAppointment("2");

        // View updated appointments
        System.out.println("\n--- Viewing Updated Appointments ---");
        bookingService.viewAppointments().forEach(System.out::println);
    }
}
