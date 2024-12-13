package com.example.healthcare.controller;

import com.example.healthcare.dto.AppointmentRequestDTO;
import com.example.healthcare.dto.AppointmentResponseDTO;
import com.example.healthcare.model.Appointment;
import com.example.healthcare.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/create")
    public ResponseEntity<AppointmentResponseDTO> createAppointment(@RequestBody AppointmentRequestDTO request) {
        Appointment appointment = appointmentService.createAppointment(
            request.getDoctorId(),
            request.getPatientId(),
            request.getAppointmentStartTime(),
            request.getAppointmentEndTime(),
            request.getReason()
        );

        AppointmentResponseDTO response = new AppointmentResponseDTO();
        response.setAppointmentId(appointment.getId());
        response.setDoctorName(appointment.getDoctor().getFirstName() + " " + appointment.getDoctor().getLastName());
        response.setPatientName(appointment.getPatient().getFirstName() + " " + appointment.getPatient().getLastName());
        response.setAppointmentStartTime(appointment.getAppointmentStartTime());
        response.setAppointmentEndTime(appointment.getAppointmentEndTime());
        response.setReason(appointment.getReason());
        response.setStatus(appointment.getStatus());

        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{id}/accept")
    public ResponseEntity<String> acceptAppointment(@PathVariable Long id) {
        appointmentService.acceptAppointment(id);
        return ResponseEntity.ok("Appointment with ID " + id + " has been accepted");
    }


    @PutMapping("/{id}/cancel")
    public ResponseEntity<String> cancelAppointment(@PathVariable Long id) {
        appointmentService.cancelAppointment(id);
        return ResponseEntity.ok("Appointment with ID " + id + " has been canceled");
    }

    @PutMapping("/{id}/reschedule")
    public ResponseEntity<AppointmentResponseDTO> rescheduleAppointment(
            @PathVariable Long id,
            @RequestBody AppointmentRequestDTO request) {
        Appointment updatedAppointment = appointmentService.rescheduleAppointment(
                id,
                request.getAppointmentStartTime(),
                request.getAppointmentEndTime()
        );

        AppointmentResponseDTO response = new AppointmentResponseDTO();
        response.setAppointmentId(updatedAppointment.getId());
        response.setDoctorName(updatedAppointment.getDoctor().getFirstName() + " " + updatedAppointment.getDoctor().getLastName());
        response.setPatientName(updatedAppointment.getPatient().getFirstName() + " " + updatedAppointment.getPatient().getLastName());
        response.setAppointmentStartTime(updatedAppointment.getAppointmentStartTime());
        response.setAppointmentEndTime(updatedAppointment.getAppointmentEndTime());
        response.setReason(updatedAppointment.getReason());
        response.setStatus(updatedAppointment.getStatus());

        return ResponseEntity.ok(response);
    }


    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<AppointmentResponseDTO>> getAppointmentsByPatient(@PathVariable Long patientId) {
        List<Appointment> appointments = appointmentService.getAppointmentsByPatient(patientId);

        List<AppointmentResponseDTO> response = appointments.stream().map(appointment -> {
            AppointmentResponseDTO dto = new AppointmentResponseDTO();
            dto.setAppointmentId(appointment.getId());
            dto.setDoctorName(appointment.getDoctor().getFirstName() + " " + appointment.getDoctor().getLastName());
            dto.setPatientName(appointment.getPatient().getFirstName() + " " + appointment.getPatient().getLastName());
            dto.setAppointmentStartTime(appointment.getAppointmentStartTime());
            dto.setAppointmentEndTime(appointment.getAppointmentEndTime());
            dto.setReason(appointment.getReason());
            dto.setStatus(appointment.getStatus());
            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

}
