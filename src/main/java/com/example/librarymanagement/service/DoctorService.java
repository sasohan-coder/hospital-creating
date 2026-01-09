package com.example.librarymanagement.service;

import com.example.librarymanagement.model.Doctor;
import com.example.librarymanagement.repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    private final DoctorRepository repository;

    public DoctorService(DoctorRepository repository) {
        this.repository = repository;
    }

    public List<Doctor> getALL() {
        return repository.findAll();
    }

    public Doctor save(Doctor doctor) {
        Optional<Doctor> optional = repository.findById(doctor.getEmail());
        if (optional.isPresent()) {
            System.out.println("Doctor already exists");
            return null;
        }
        repository.save(doctor);
        return doctor;
    }

    public boolean hasNoChild(String email) {
        Doctor doctor = repository.findById(email).orElse(null);
        return doctor != null && doctor.getAppointmentList().isEmpty();
    }

    public void delete(String email) {
        repository.deleteById(email);
    }

    public void update(Doctor doctor) {
        repository.save(doctor);
    }
}