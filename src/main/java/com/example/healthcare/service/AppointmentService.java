package com.example.healthcare.service;

import com.example.healthcare.interfaces.IManageAppointmentRequest;
import com.example.healthcare.interfaces.IViewAppointment;

import com.example.healthcare.model.Appointment;
import com.example.healthcare.model.Doctor;
import com.example.healthcare.model.DoctorUnavailability;
import com.example.healthcare.repository.AppointmentRepository;
import com.example.healthcare.repository.DoctorRepository;
import com.example.healthcare.repository.PatientRepository;
import com.example.healthcare.repository.DoctorUnavailabilityRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentService implements IManageAppointmentRequest, IViewAppointment{

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;
    
    @Autowired
    private DoctorUnavailabilityRepository doctorUnavailabilityRepository;
    
    @Autowired
    private EmailService emailService;

    public Appointment createAppointment(Long doctorId, Long patientId, LocalDateTime appointmentTime, String reason) {
        // Check if slot is within doctor's working hours
        Doctor doctor = doctorRepository.findById(doctorId)
            .orElseThrow(() -> new RuntimeException("Doctor not found"));

        LocalTime slotTime = appointmentTime.toLocalTime();
        if (slotTime.isBefore(doctor.getWorkingHoursStart()) || slotTime.isAfter(doctor.getWorkingHoursEnd())) {
            throw new RuntimeException("Appointment time is outside doctor's working hours");
        }

        // Check for doctor unavailability
        boolean isDoctorUnavailable = doctorUnavailabilityRepository.existsByDoctorAndTimeRange(
            doctorId, appointmentTime, appointmentTime.plusHours(1));
        if (isDoctorUnavailable) {
            throw new RuntimeException("Doctor is unavailable at the selected time");
        }

        // Check if the slot is already booked
        boolean isSlotBooked = appointmentRepository.existsByDoctorAndAppointmentTime(doctorId, appointmentTime);
        if (isSlotBooked) {
            throw new RuntimeException("The selected slot is already booked");
        }

        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setPatient(patientRepository.findById(patientId)
            .orElseThrow(() -> new RuntimeException("Patient not found")));
        appointment.setAppointmentTime(appointmentTime);
        appointment.setReason(reason);
        appointment.setStatus("PENDING");

        return appointmentRepository.save(appointment);
    }

    public Appointment acceptAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        if (!"PENDING".equals(appointment.getStatus())) {
            throw new RuntimeException("Only pending appointments can be accepted");
        }

        appointment.setStatus("ACCEPTED");
        Appointment savedAppointment = appointmentRepository.save(appointment);

        // Send email notification to the patient
        String patientEmail = appointment.getPatient().getEmail();
        String doctorName = appointment.getDoctor().getFirstName() + " " + appointment.getDoctor().getLastName();
        LocalDateTime appointmentStartTime = appointment.getAppointmentTime();

        emailService.sendAppointmentAcceptedNotification(patientEmail, doctorName, appointmentStartTime);

        return savedAppointment;
    }
    
    public Appointment rejectAppointment(Long appointmentId, String rejectionReason) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));

        if (!"PENDING".equals(appointment.getStatus()) && !"RESCHEDULED".equals(appointment.getStatus())) {
            throw new RuntimeException("Only pending or rescheduled appointments can be rejected");
        }

        appointment.setStatus("REJECTED");
        Appointment updatedAppointment = appointmentRepository.save(appointment);

        // Send rejection email
        String patientEmail = appointment.getPatient().getEmail();
        String doctorName = appointment.getDoctor().getFirstName() + " " + appointment.getDoctor().getLastName();
        emailService.sendAppointmentRejectionNotification(patientEmail, doctorName, rejectionReason);

        return updatedAppointment;
    }
    
    public void cancelAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        appointment.setStatus("CANCELLED");
        
        if ("CANCELLED".equals(appointment.getStatus())) {
        	throw new RuntimeException("Appointment has already been cancelled");
        }
        
        appointmentRepository.save(appointment);
    }
    
    public List<LocalDateTime> getAvailableSlots(Long doctorId, LocalDate date) {
                Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        LocalTime startTime = doctor.getWorkingHoursStart();
        LocalTime endTime = doctor.getWorkingHoursEnd();

                List<LocalDateTime> allSlots = generateTimeSlots(date, startTime, endTime, 60);

        LocalDateTime dayStart = date.atStartOfDay();
        LocalDateTime dayEnd = date.atTime(23, 59);

        List<LocalDateTime> bookedSlots = appointmentRepository.findByDoctorIdAndAppointmentTimeBetween(doctorId, dayStart, dayEnd)
                .stream()
                .map(appointment -> appointment.getAppointmentTime())
                .toList();

        List<DoctorUnavailability> unavailabilities = doctorUnavailabilityRepository.findAllByDoctorId(doctorId);

        return allSlots.stream()
                .filter(slot -> !isSlotBookedOrUnavailable(slot, bookedSlots, unavailabilities))
                .toList();
    }

    private boolean isSlotBookedOrUnavailable(LocalDateTime slot, List<LocalDateTime> bookedSlots, List<DoctorUnavailability> unavailabilities) {
        if (bookedSlots.contains(slot)) {
            return true;
        }

        for (DoctorUnavailability unavailability : unavailabilities) {
            if (!slot.isBefore(unavailability.getUnavailableFrom()) && !slot.isAfter(unavailability.getUnavailableTo())) {
                return true;
            }
        }

        return false;
    }

    private List<LocalDateTime> generateTimeSlots(LocalDate date, LocalTime startTime, LocalTime endTime, int intervalMinutes) {
        List<LocalDateTime> slots = new ArrayList<>();
        LocalDateTime currentSlot = date.atTime(startTime);

        while (currentSlot.isBefore(date.atTime(endTime))) {
            slots.add(currentSlot);
            currentSlot = currentSlot.plusMinutes(intervalMinutes);
        }

        return slots;
    }


    public List<Appointment> getAppointmentsByPatient(Long patientId) {
        return appointmentRepository.findByPatientIdOrderByAppointmentTimeAsc(patientId);
    }

    public List<Appointment> getAppointmentsByDoctor(Long doctorId) {
        return appointmentRepository.findByDoctorIdOrderByAppointmentTimeAsc(doctorId);
    }

    public List<Appointment> getAppointmentsByPatientDaily(Long patientId) {
        LocalDateTime startOfDay = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);
        return appointmentRepository.findByPatientIdAndAppointmentTimeBetween(patientId, startOfDay, endOfDay);
    }

    public List<Appointment> getAppointmentsByPatientWeekly(Long patientId) {
        LocalDateTime startOfWeek = LocalDateTime.now().with(java.time.DayOfWeek.MONDAY).toLocalDate().atStartOfDay();
        LocalDateTime endOfWeek = startOfWeek.plusWeeks(1);
        return appointmentRepository.findByPatientIdAndAppointmentTimeBetween(patientId, startOfWeek, endOfWeek);
    }

    public List<Appointment> getAppointmentsByPatientMonthly(Long patientId) {
        LocalDateTime startOfMonth = LocalDateTime.now().withDayOfMonth(1).toLocalDate().atStartOfDay();
        LocalDateTime endOfMonth = startOfMonth.plusMonths(1);
        return appointmentRepository.findByPatientIdAndAppointmentTimeBetween(patientId, startOfMonth, endOfMonth);
    }

    public List<Appointment> getAppointmentsByDoctorDaily(Long doctorId) {
        LocalDateTime startOfDay = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = startOfDay.plusDays(1);
        return appointmentRepository.findByDoctorIdAndAppointmentTimeBetween(doctorId, startOfDay, endOfDay);
    }

    public List<Appointment> getAppointmentsByDoctorWeekly(Long doctorId) {
        LocalDateTime startOfWeek = LocalDateTime.now().with(java.time.DayOfWeek.MONDAY).toLocalDate().atStartOfDay();
        LocalDateTime endOfWeek = startOfWeek.plusWeeks(1);
        return appointmentRepository.findByDoctorIdAndAppointmentTimeBetween(doctorId, startOfWeek, endOfWeek);
    }

    public List<Appointment> getAppointmentsByDoctorMonthly(Long doctorId) {
        LocalDateTime startOfMonth = LocalDateTime.now().withDayOfMonth(1).toLocalDate().atStartOfDay();
        LocalDateTime endOfMonth = startOfMonth.plusMonths(1);
        return appointmentRepository.findByDoctorIdAndAppointmentTimeBetween(doctorId, startOfMonth, endOfMonth);
    }

    public Appointment rescheduleAppointment(Long appointmentId, LocalDateTime newTime) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
            .orElseThrow(() -> new RuntimeException("Appointment not found"));

        if ("CANCELLED".equals(appointment.getStatus())) {
            throw new RuntimeException("Cannot reschedule a canceled appointment");
        }

        Doctor doctor = appointment.getDoctor();

        // Check if new time is within doctor's working hours
        LocalTime slotTime = newTime.toLocalTime();
        if (slotTime.isBefore(doctor.getWorkingHoursStart()) || slotTime.isAfter(doctor.getWorkingHoursEnd())) {
            throw new RuntimeException("New appointment time is outside doctor's working hours");
        }

        // Check for doctor unavailability
        boolean isDoctorUnavailable = doctorUnavailabilityRepository.existsByDoctorAndTimeRange(
            doctor.getId(), newTime, newTime.plusHours(1));
        if (isDoctorUnavailable) {
            throw new RuntimeException("Doctor is unavailable at the new time");
        }

        // Check if the new time slot is already booked
        boolean isSlotBooked = appointmentRepository.existsByDoctorAndAppointmentTime(doctor.getId(), newTime);
        if (isSlotBooked) {
            throw new RuntimeException("The new slot is already booked");
        }

        // Update appointment time and status
        appointment.setAppointmentTime(newTime);
        appointment.setStatus("RESCHEDULED");

        return appointmentRepository.save(appointment);
    }

    
    public List<Appointment> getAppointmentsForReminders(LocalDateTime now, LocalDateTime reminderTime) {
        return appointmentRepository.findAppointmentsForReminders(now, reminderTime);
    }

    public void updateAppointment(Appointment appointment) {
        appointmentRepository.save(appointment);
    }

}
