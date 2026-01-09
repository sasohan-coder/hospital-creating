package com.example.librarymanagement.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "appointments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String symptoms;
    private String diagnosis;
    private String status; // SCHEDULED, COMPLETED, CANCELLED
    private LocalDate bookingDate;

    public Appointment(Doctor doctor, Patient patient, LocalDate appointmentDate, LocalTime appointmentTime,
                       String symptoms, String status, LocalDate bookingDate) {
        this.doctor = doctor;
        this.patient = patient;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.symptoms = symptoms;
        this.status = status;
        this.bookingDate = bookingDate;
    }
}