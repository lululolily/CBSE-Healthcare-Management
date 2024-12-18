package com.healthcare.impl;

import com.healthcare.api.service.BookingService;
import com.healthcare.impl.impl.BookingServiceImpl;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {

    private ServiceRegistration<BookingService> registration;

    @Override
    public void start(BundleContext context) {
        System.out.println("Starting Booking Service Bundle...");
        
        // Initialize the Booking Service
        BookingService bookingService = new BookingServiceImpl();
        registration = context.registerService(BookingService.class, bookingService, null);
        System.out.println("Booking Service Registered!");
    }

    @Override
    public void stop(BundleContext context) {
        System.out.println("Stopping Booking Service Bundle...");
        if (registration != null) {
            registration.unregister();
            System.out.println("Booking Service Unregistered.");
        }
    }
}

