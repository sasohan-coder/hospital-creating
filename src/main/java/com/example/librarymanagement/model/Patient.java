package com.example.librarymanagement.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "patients")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Patient {
    @Id
    private String email;
    private String name;
    private String phone;
    private String address;
    private LocalDate dateOfBirth;
    private String bloodGroup;
    private LocalDate registrationDate;

    @OneToMany(mappedBy = "patient")
    private List<Appointment> appointmentList;

    public Patient(String name, String email, String phone, String address, LocalDate dateOfBirth, String bloodGroup, LocalDate registrationDate) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.bloodGroup = bloodGroup;
        this.registrationDate = registrationDate;
    }
}