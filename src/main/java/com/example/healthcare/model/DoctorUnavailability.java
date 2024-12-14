package com.example.healthcare.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class DoctorUnavailability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "doctor_id", referencedColumnName = "id")
    private Doctor doctor;

    private LocalDateTime unavailableFrom;
    private LocalDateTime unavailableTo;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
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
}
