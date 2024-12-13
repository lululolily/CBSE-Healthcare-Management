package com.example.healthcare.repository;

import com.example.healthcare.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByUserId(Long userId); // To fetch doctor details using their User ID
    List<Doctor> findBySpecialization(String specialization); // To find doctors by specialization
}
