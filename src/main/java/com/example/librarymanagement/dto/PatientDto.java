package com.example.librarymanagement.dto;

import com.example.librarymanagement.model.Patient;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public record PatientDto(String name, String email, String phone, String address,
                         String dateOfBirth, String bloodGroup) {

    public Patient toPatient() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dob = LocalDate.parse(dateOfBirth, formatter);
        return new Patient(name, email, phone, address, dob, bloodGroup, LocalDate.now());
    }
}