package com.example.healthcare.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.healthcare.interfaces.IAppointmentNotification;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

@Service
public class EmailService implements IAppointmentNotification {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    public void sendAppointmentAcceptedNotification(String email, String doctorName, LocalDateTime appointmentTime) {
        String subject = "Appointment Accepted - Dr. " + doctorName;
        String body = buildAppointmentNotificationBody(doctorName, appointmentTime, "accepted");
        sendEmail(email, subject, body);
        System.out.println("Appointment accepted notification sent to: " + email + " for appointment at: " + appointmentTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }

    public void sendAppointmentReminder(String email, String doctorName, LocalDateTime appointmentTime) {
        String subject = "Appointment Reminder - Dr. " + doctorName;
        String body = buildAppointmentNotificationBody(doctorName, appointmentTime, "reminder");
        sendEmail(email, subject, body);
        System.out.println("Reminder email sent to: " + email + " for appointment at: " + appointmentTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
    }
    
    public void sendAppointmentRejectionNotification(String email, String doctorName, String reason) {
        String subject = "Appointment Rejection Notification";
        String body = "Dear Patient,\n\n"
                + "We regret to inform you that your appointment request with Dr. " + doctorName + " has been rejected.\n\n"
                + "Reason: " + reason + "\n\n"
                + "If you have any questions or would like to schedule another appointment, please contact the clinic directly.\n\n"
                + "Best regards,\n"
                + "Healthcare Team";

        sendEmail(email, subject, body);
        System.out.println("Appointment rejection email sent to: " + email);
    }

    private String buildAppointmentNotificationBody(String doctorName, LocalDateTime appointmentTime, String type) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy 'at' hh:mm a");
        String formattedDateTime = appointmentTime.format(formatter);

        String messageType = "accepted".equals(type) ? "has been accepted" : "is scheduled";
        return "Dear Patient,\n\n"
             + "Your appointment request " + messageType + ".\n\n"
             + "Details:\n"
             + "Doctor: Dr. " + doctorName + "\n"
             + "Date & Time: " + formattedDateTime + "\n\n"
             + "Please ensure to arrive on time for your appointment.\n\n"
             + "If you have any questions or need to reschedule, please contact the clinic directly.\n\n"
             + "Best regards,\n"
             + "Healthcare Team";
    }
}
