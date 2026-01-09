package com.example.librarymanagement.dto;

import com.example.librarymanagement.model.Doctor;

public record DoctorDto(String name, String email, String phone, String specialization,
                        String qualification, Double consultationFee) {

    public Doctor toDoctor() {
        return new Doctor(name, email, phone, specialization, qualification, consultationFee);
    }
}