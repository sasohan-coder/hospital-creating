package com.example.librarymanagement.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "appointment_id", nullable = true)
    private Appointment appointment;

    private Double amount;
    private String paymentMethod; // CASH, CARD, ONLINE
    private String transactionId;
    private String paymentFor; // CONSULTATION, MEDICINE, TEST, OTHER
    private String remarks;
    private LocalDate paymentDate;
    private LocalDateTime createdAt;

    public Payment(Patient patient, Appointment appointment, Double amount, 
                   String paymentMethod, String transactionId, String paymentFor, 
                   String remarks, LocalDate paymentDate) {
        this.patient = patient;
        this.appointment = appointment;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.transactionId = transactionId;
        this.paymentFor = paymentFor;
        this.remarks = remarks;
        this.paymentDate = paymentDate;
        this.createdAt = LocalDateTime.now();
    }
}