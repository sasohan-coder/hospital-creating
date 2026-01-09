package com.example.librarymanagement.service;

import com.example.librarymanagement.model.Payment;
import com.example.librarymanagement.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository repository;

    public PaymentService(PaymentRepository repository) {
        this.repository = repository;
    }

    public List<Payment> getAll() {
        return repository.findAll();
    }

    public Payment save(Payment payment) {
        return repository.save(payment);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }

    public Payment getById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public List<Payment> getByDate(LocalDate date) {
        return repository.findByPaymentDate(date);
    }

    public List<Payment> getByDateRange(LocalDate startDate, LocalDate endDate) {
        return repository.findByPaymentDateBetween(startDate, endDate);
    }

    public List<Payment> getByPatientEmail(String email) {
        return repository.findByPatientEmail(email);
    }

    public Double getTotalPayments() {
        Double total = repository.getTotalPaymentAmount();
        return total != null ? total : 0.0;
    }

    public Double getTotalPaymentsByDate(LocalDate date) {
        Double total = repository.getTotalPaymentByDate(date);
        return total != null ? total : 0.0;
    }
}