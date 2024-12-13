package com.example.healthcare.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    public void sendAppointmentReminder(String email, String doctorName, String appointmentTime) {
        String subject = "Appointment Reminder";
        String body = "You have an upcoming appointment with Dr. " + doctorName + " on " + appointmentTime + ".";
        sendEmail(email, subject, body);
    }
}
