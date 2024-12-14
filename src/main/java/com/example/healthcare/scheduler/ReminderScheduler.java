package com.example.healthcare.scheduler;

import com.example.healthcare.model.Appointment;
import com.example.healthcare.service.AppointmentService;
import com.example.healthcare.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/* For Testing */
//@Component
//public class ReminderScheduler {
//
//    @Autowired
//    private AppointmentService appointmentService;
//
//    @Autowired
//    private EmailService emailService;
//
//    public void testSendReminders() {
//        LocalDateTime now = LocalDateTime.now();
//        LocalDateTime reminderTime = now.plusHours(24);
//
//        List<Appointment> upcomingAppointments = appointmentService.getAppointmentsForReminders(now, reminderTime);
//        
//        for (Appointment appointment : upcomingAppointments) {
//            String patientEmail = appointment.getPatient().getEmail();
//            String doctorName = appointment.getDoctor().getFirstName() + " " + appointment.getDoctor().getLastName();
//            LocalDateTime appointmentTime = appointment.getAppointmentStartTime();
//
//            emailService.sendAppointmentReminder(patientEmail, doctorName, appointmentTime);
//
//            // Mark the reminder as sent
//            appointment.setReminderSent(true);
//            appointmentService.updateAppointment(appointment);
//        }
//    }
//
//}

@Component
public class ReminderScheduler {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private EmailService emailService;

    @Scheduled(cron = "0 */1 * * * ?")
    public void sendReminders() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime reminderTime = now.plusHours(24);

        List<Appointment> upcomingAppointments = appointmentService.getAppointmentsForReminders(now, reminderTime);

        for (Appointment appointment : upcomingAppointments) {
            String patientEmail = appointment.getPatient().getEmail();
            String doctorName = appointment.getDoctor().getFirstName() + " " + appointment.getDoctor().getLastName();
            LocalDateTime appointmentTime = appointment.getAppointmentTime();

            emailService.sendAppointmentReminder(patientEmail, doctorName, appointmentTime);

            // Mark the reminder as sent
            appointment.setReminderSent(true);
            appointmentService.updateAppointment(appointment);
        }
    }
}
