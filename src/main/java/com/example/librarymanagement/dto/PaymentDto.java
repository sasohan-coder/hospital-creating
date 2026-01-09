package com.example.librarymanagement.dto;

import com.example.librarymanagement.model.Appointment;
import com.example.librarymanagement.model.Patient;
import com.example.librarymanagement.model.Payment;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public record PaymentDto(Patient patient, Appointment appointment, String amount,
                         String paymentMethod, String transactionId, String paymentFor,
                         String remarks, String paymentDate) {

    public Payment toPayment() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(paymentDate, formatter);
        Double amt = Double.parseDouble(amount);

        return new Payment(patient, appointment, amt, paymentMethod, 
                          transactionId, paymentFor, remarks, date);
    }
}