package com.example.librarymanagement.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "doctors")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Doctor {
    @Id
    private String email;
    private String name;
    private String phone;
    private String specialization;
    private String qualification;
    private Double consultationFee;

    @OneToMany(mappedBy = "doctor")
    private List<Appointment> appointmentList;

    public Doctor(String name, String email, String phone, String specialization, String qualification, Double consultationFee) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.specialization = specialization;
        this.qualification = qualification;
        this.consultationFee = consultationFee;
    }
}