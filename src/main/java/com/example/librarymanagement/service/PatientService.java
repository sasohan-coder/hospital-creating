package com.example.librarymanagement.service;

import com.example.librarymanagement.model.Patient;
import com.example.librarymanagement.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    private final PatientRepository repository;

    public PatientService(PatientRepository repository) {
        this.repository = repository;
    }

    public List<Patient> getALL() {
        return repository.findAll();
    }

    public Patient save(Patient patient) {
        Optional<Patient> optional = repository.findById(patient.getEmail());
        if (optional.isPresent()) {
            System.out.println("Patient already exists");
            return null;
        }
        repository.save(patient);
        return patient;
    }

    public boolean hasNoChild(String email) {
        Patient patient = repository.findById(email).orElse(null);
        return patient != null && patient.getAppointmentList().isEmpty();
    }

    public void delete(String email) {
        repository.deleteById(email);
    }

    public void update(Patient patient) {
        repository.save(patient);
    }
}