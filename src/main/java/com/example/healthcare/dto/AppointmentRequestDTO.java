package com.example.healthcare.dto;

import java.time.LocalDateTime;

public class AppointmentRequestDTO {
    private Long doctorId;
    private Long patientId;
    private LocalDateTime appointmentTime; 
    private String reason;

    // Getters and Setters
    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

	public LocalDateTime getAppointmentTime() {
		return appointmentTime;
	}

	public void setAppointmentTime(LocalDateTime appointmentTime) {
		this.appointmentTime = appointmentTime;
	}
	
    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
