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
    List<Appointment> findByDoctorIdAndAppointmentStartTimeBetween(Long doctorId, LocalDateTime start, LocalDateTime end);

    List<Appointment> findByPatientIdOrderByAppointmentStartTimeAsc(Long patientId);

    List<Appointment> findByDoctorIdOrderByAppointmentStartTimeAsc(Long doctorId);

    @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END " +
            "FROM Appointment a " +
            "WHERE a.doctor.id = :doctorId " +
            "AND a.status != 'CANCELLED' " +
            "AND (a.appointmentStartTime < :endTime AND a.appointmentEndTime > :startTime)")
     boolean existsByDoctorAndTimeRange(
             @Param("doctorId") Long doctorId,
             @Param("startTime") LocalDateTime startTime,
             @Param("endTime") LocalDateTime endTime);
}
