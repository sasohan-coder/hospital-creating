package com.example.librarymanagement.service;

import com.example.librarymanagement.model.Appointment;
import com.example.librarymanagement.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    private final AppointmentRepository repository;

    public AppointmentService(AppointmentRepository repository) {
        this.repository = repository;
    }

    public List<Appointment> getALL() {
        return repository.findAll();
    }

    public Appointment save(Appointment appointment) {
        Optional<Appointment> optional = repository.findById(appointment.getId());
        if (optional.isPresent()) {
            System.out.println("Appointment already exists");
            return null;
        }
        repository.save(appointment);
        return appointment;
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }

    public void update(Appointment appointment) {
        repository.save(appointment);
    }

    public Appointment getById(Integer id) {
        return repository.findById(id).orElse(null);
    }
}