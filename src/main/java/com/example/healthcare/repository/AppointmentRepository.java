package com.example.healthcare.repository;

import com.example.healthcare.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("SELECT a FROM Appointment a WHERE a.doctor.id = :doctorId " +
           "AND a.appointmentTime BETWEEN :from AND :to " +
           "AND a.status != 'CANCELLED'")
    List<Appointment> findByDoctorIdAndAppointmentTimeBetween(
            @Param("doctorId") Long doctorId,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to);

    List<Appointment> findByPatientIdAndAppointmentTimeBetween(Long patientId, LocalDateTime start, LocalDateTime end);

    List<Appointment> findByPatientIdOrderByAppointmentTimeAsc(Long patientId);

    List<Appointment> findByDoctorIdOrderByAppointmentTimeAsc(Long doctorId);

    List<Appointment> findByAppointmentTimeBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END " +
           "FROM Appointment a " +
           "WHERE a.doctor.id = :doctorId " +
           "AND a.status != 'CANCELLED' " +
           "AND a.appointmentTime = :appointmentTime")
    boolean existsByDoctorAndAppointmentTime(
            @Param("doctorId") Long doctorId,
            @Param("appointmentTime") LocalDateTime appointmentTime);

    @Query("SELECT a FROM Appointment a WHERE a.appointmentTime BETWEEN :now AND :reminderTime " +
           "AND a.status = 'ACCEPTED' AND a.reminderSent = false")
    List<Appointment> findAppointmentsForReminders(@Param("now") LocalDateTime now, @Param("reminderTime") LocalDateTime reminderTime);
}
