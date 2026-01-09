package com.example.librarymanagement.dto;

import com.example.librarymanagement.model.Appointment;
import com.example.librarymanagement.model.Doctor;
import com.example.librarymanagement.model.Patient;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public record AppointmentDto(Doctor doctor, Patient patient, String appointmentDate,
                             String appointmentTime, String symptoms, String status) {

    public Appointment toAppointment() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        LocalDate apptDate = LocalDate.parse(appointmentDate, dateFormatter);
        LocalTime apptTime = LocalTime.parse(appointmentTime, timeFormatter);

        return new Appointment(doctor, patient, apptDate, apptTime, symptoms,
                status != null && !status.isEmpty() ? status : "SCHEDULED",
                LocalDate.now());
    }
}