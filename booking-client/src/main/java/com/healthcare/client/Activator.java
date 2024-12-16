package com.healthcare.client;

import com.healthcare.api.service.BookingService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class Activator implements BundleActivator {

    private ServiceReference<BookingService> reference;

    @Override
    public void start(BundleContext context) {
        System.out.println("Starting Booking Client Bundle...");
        reference = context.getServiceReference(BookingService.class);
        if (reference != null) {
            BookingService bookingService = context.getService(reference);
            System.out.println("Booking Service Retrieved: " + bookingService);
        } else {
            System.out.println("Booking Service not found.");
        }
    }

    @Override
    public void stop(BundleContext context) {
        System.out.println("Stopping Booking Client Bundle...");
        if (reference != null) {
            context.ungetService(reference);
            System.out.println("Booking Service Released.");
        }
    }
}
