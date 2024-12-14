package com.example.healthcare.interfaces;

import com.example.healthcare.model.Appointment;
import java.util.List;

public interface IViewAppointment {
    List<Appointment> getAppointmentsByPatient(Long patientId);

    List<Appointment> getAppointmentsByPatientDaily(Long patientId);

    List<Appointment> getAppointmentsByPatientWeekly(Long patientId);

    List<Appointment> getAppointmentsByPatientMonthly(Long patientId);

    List<Appointment> getAppointmentsByDoctor(Long doctorId);

    List<Appointment> getAppointmentsByDoctorDaily(Long doctorId);

    List<Appointment> getAppointmentsByDoctorWeekly(Long doctorId);

    List<Appointment> getAppointmentsByDoctorMonthly(Long doctorId);
}
