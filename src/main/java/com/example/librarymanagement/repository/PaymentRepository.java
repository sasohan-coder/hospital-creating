package com.example.librarymanagement.repository;

import com.example.librarymanagement.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    // Find payments by date range
    List<Payment> findByPaymentDateBetween(LocalDate startDate, LocalDate endDate);

    // Find payments by specific date
    List<Payment> findByPaymentDate(LocalDate paymentDate);

    // Find payments by patient email
    List<Payment> findByPatientEmail(String email);

    // Calculate total payment amount
    @Query("SELECT SUM(p.amount) FROM Payment p")
    Double getTotalPaymentAmount();

    // Calculate total payment for a specific date
    @Query("SELECT SUM(p.amount) FROM Payment p WHERE p.paymentDate = :date")
    Double getTotalPaymentByDate(LocalDate date);
}