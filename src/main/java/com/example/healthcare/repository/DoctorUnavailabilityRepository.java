package com.example.healthcare.repository;

import com.example.healthcare.model.DoctorUnavailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DoctorUnavailabilityRepository extends JpaRepository<DoctorUnavailability, Long> {

    @Query("SELECT u FROM DoctorUnavailability u WHERE u.doctor.id = :doctorId " +
            "AND (:startTime < u.unavailableTo AND :endTime > u.unavailableFrom)")
    List<DoctorUnavailability> findOverlappingUnavailabilities(
            @Param("doctorId") Long doctorId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);

    List<DoctorUnavailability> findAllByDoctorId(Long doctorId);
    
    @Query("SELECT CASE WHEN COUNT(d) > 0 THEN true ELSE false END " +
    	       "FROM DoctorUnavailability d " +
    	       "WHERE d.doctor.id = :doctorId " +
    	       "AND (d.unavailableFrom <= :endTime AND d.unavailableTo >= :startTime)")
    	boolean existsByDoctorAndTimeRange(
    	    @Param("doctorId") Long doctorId,
    	    @Param("startTime") LocalDateTime startTime,
    	    @Param("endTime") LocalDateTime endTime);

}
