package com.healthcare.api.model;

import java.time.LocalDateTime;

public class Appointment {
    private String id;
    private String doctorId;
    private String patientId;
    private String patientNotes;
    private String reason;
    private String dateTime;
    private AppointmentStatus status;
    private boolean reminderSent;
    private LocalDateTime unavailableFrom;
    private LocalDateTime unavailableTo;

    // Constructor
    public Appointment(String id, String doctorId, String patientId, String patientNotes, String reason, String dateTime) {
        this.id = id;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.patientNotes = patientNotes;
        this.reason = reason;
        this.dateTime = dateTime;
        this.status = AppointmentStatus.PENDING; // Default status to pending
        this.reminderSent = false; // Default reminder status to false
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientNotes() {
        return patientNotes;
    }

    public void setPatientNotes(String patientNotes) {
        this.patientNotes = patientNotes;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public boolean isReminderSent() {
        return reminderSent;
    }

    public void setReminderSent(boolean reminderSent) {
        this.reminderSent = reminderSent;
    }
    
    public void DoctorAvailability(String doctorId, LocalDateTime unavailableFrom, LocalDateTime unavailableTo) {
        this.doctorId = doctorId;
        this.unavailableFrom = unavailableFrom;
        this.unavailableTo = unavailableTo;
    }
    
    public LocalDateTime getUnavailableFrom() {
        return unavailableFrom;
    }

    public void setUnavailableFrom(LocalDateTime unavailableFrom) {
        this.unavailableFrom = unavailableFrom;
    }

    public LocalDateTime getUnavailableTo() {
        return unavailableTo;
    }

    public void setUnavailableTo(LocalDateTime unavailableTo) {
        this.unavailableTo = unavailableTo;
    }

    @Override
    public String toString() {
        return "Appointment:" +
                "Appointment ID='" + id + '\'' +
                ", Doctor ID='" + doctorId + '\'' +
                ", Patient ID='" + patientId + '\'' +
                ", Patient Notes='" + patientNotes + '\'' +
                ", Reason='" + reason + '\'' +
                ", Appointment Time='" + dateTime + '\'' +
                ", Appointment Status=" + status +
                ", Reminder=" + reminderSent +
                '}';
    }
}
