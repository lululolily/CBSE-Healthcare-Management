package com.example.healthcare.controller;

import com.example.healthcare.scheduler.ReminderScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * This controller is used for testing purposes only.
 */

@SuppressWarnings("unused")
@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private ReminderScheduler reminderScheduler;

//    @GetMapping("/send-reminders")
//    public String testReminderEmails() {
//        reminderScheduler.testSendReminders();
//        return "Reminders sent successfully!";
//    }
}
