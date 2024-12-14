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
            request.getAppointmentTime(),
            request.getReason()
        );

        AppointmentResponseDTO response = new AppointmentResponseDTO();
        response.setAppointmentId(appointment.getId());
        response.setDoctorName(appointment.getDoctor().getFirstName() + " " + appointment.getDoctor().getLastName());
        response.setPatientName(appointment.getPatient().getFirstName() + " " + appointment.getPatient().getLastName());
        response.setAppointmentTime(appointment.getAppointmentTime());
        response.setReason(appointment.getReason());
        response.setStatus(appointment.getStatus());

        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{id}/accept")
    public ResponseEntity<String> acceptAppointment(@PathVariable Long id) {
        appointmentService.acceptAppointment(id);
        return ResponseEntity.ok("Appointment with ID " + id + " has been accepted");
    }
    
    @PutMapping("/{id}/reject")
    public ResponseEntity<String> rejectAppointment(
            @PathVariable Long id,
            @RequestParam String reason) {
        appointmentService.rejectAppointment(id, reason);
        return ResponseEntity.ok("Appointment with ID " + id + " has been rejected for reason: " + reason);
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
                request.getAppointmentTime()
        );

        AppointmentResponseDTO response = new AppointmentResponseDTO();
        response.setAppointmentId(updatedAppointment.getId());
        response.setDoctorName(updatedAppointment.getDoctor().getFirstName() + " " + updatedAppointment.getDoctor().getLastName());
        response.setPatientName(updatedAppointment.getPatient().getFirstName() + " " + updatedAppointment.getPatient().getLastName());
        response.setAppointmentTime(updatedAppointment.getAppointmentTime());
        response.setReason(updatedAppointment.getReason());
        response.setStatus(updatedAppointment.getStatus());

        return ResponseEntity.ok(response);
    }


    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<AppointmentResponseDTO>> getAppointmentsByPatient(
            @PathVariable Long patientId,
            @RequestParam(value = "filter", required = false) String filter) {
        List<Appointment> appointments;

        // Apply filter based on the query parameter
        if ("daily".equalsIgnoreCase(filter)) {
            appointments = appointmentService.getAppointmentsByPatientDaily(patientId);
        } else if ("weekly".equalsIgnoreCase(filter)) {
            appointments = appointmentService.getAppointmentsByPatientWeekly(patientId);
        } else if ("monthly".equalsIgnoreCase(filter)) {
            appointments = appointmentService.getAppointmentsByPatientMonthly(patientId);
        } else {
            appointments = appointmentService.getAppointmentsByPatient(patientId);
        }

        List<AppointmentResponseDTO> response = appointments.stream().map(appointment -> {
            AppointmentResponseDTO dto = new AppointmentResponseDTO();
            dto.setAppointmentId(appointment.getId());
            dto.setDoctorName(appointment.getDoctor().getFirstName() + " " + appointment.getDoctor().getLastName());
            dto.setPatientName(appointment.getPatient().getFirstName() + " " + appointment.getPatient().getLastName());
            dto.setAppointmentTime(appointment.getAppointmentTime());
            dto.setReason(appointment.getReason());
            dto.setStatus(appointment.getStatus());
            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<AppointmentResponseDTO>> getAppointmentsByDoctor(
            @PathVariable Long doctorId,
            @RequestParam(value = "filter", required = false) String filter) {
        List<Appointment> appointments;

        // Apply filter based on the query parameter
        if ("daily".equalsIgnoreCase(filter)) {
            appointments = appointmentService.getAppointmentsByDoctorDaily(doctorId);
        } else if ("weekly".equalsIgnoreCase(filter)) {
            appointments = appointmentService.getAppointmentsByDoctorWeekly(doctorId);
        } else if ("monthly".equalsIgnoreCase(filter)) {
            appointments = appointmentService.getAppointmentsByDoctorMonthly(doctorId);
        } else {
            appointments = appointmentService.getAppointmentsByDoctor(doctorId);
        }

        List<AppointmentResponseDTO> response = appointments.stream().map(appointment -> {
            AppointmentResponseDTO dto = new AppointmentResponseDTO();
            dto.setAppointmentId(appointment.getId());
            dto.setDoctorName(appointment.getDoctor().getFirstName() + " " + appointment.getDoctor().getLastName());
            dto.setPatientName(appointment.getPatient().getFirstName() + " " + appointment.getPatient().getLastName());
            dto.setAppointmentTime(appointment.getAppointmentTime());
            dto.setReason(appointment.getReason());
            dto.setStatus(appointment.getStatus());
            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

}
