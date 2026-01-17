package com.example.librarymanagement.controllers;

import com.example.librarymanagement.model.Appointment;
import com.example.librarymanagement.model.Doctor;
import com.example.librarymanagement.model.Patient;
import com.example.librarymanagement.model.Payment;
import com.example.librarymanagement.service.AppointmentService;
import com.example.librarymanagement.service.DoctorService;
import com.example.librarymanagement.service.PatientService;
import com.example.librarymanagement.service.PaymentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
public class DashboardController {

    private final PatientService patientService;
    private final DoctorService doctorService;
    private final AppointmentService appointmentService;
    private final PaymentService paymentService;

    public DashboardController(PatientService patientService,
                               DoctorService doctorService,
                               AppointmentService appointmentService,
                               PaymentService paymentService) {
        this.patientService = patientService;
        this.doctorService = doctorService;
        this.appointmentService = appointmentService;
        this.paymentService = paymentService;
    }

    @GetMapping("/")
    public String getDashboard(Model model) {

        List<Patient> patients = patientService.getALL();
        List<Doctor> doctors = doctorService.getALL();
        List<Appointment> appointments = appointmentService.getALL();
        List<Payment> payments = paymentService.getAll();

        // Total counts
        model.addAttribute("totalPatients", patients.size());
        model.addAttribute("totalDoctors", doctors.size());
        model.addAttribute("totalAppointments", appointments.size());

        // Today's appointments (assuming Appointment has getAppointmentDate())
        long todayAppointments = appointments.stream()
                .filter(a -> a.getAppointmentDate().isEqual(LocalDate.now()))
                .count();
        model.addAttribute("todayAppointments", todayAppointments);

        // Total payments
        double totalPayments = payments.stream()
                .mapToDouble(Payment::getAmount)
                .sum();
        model.addAttribute("totalPayments", totalPayments);

        // Payments today
        double todayPayments = payments.stream()
                .filter(p -> p.getPaymentDate().isEqual(LocalDate.now()))
                .mapToDouble(Payment::getAmount)
                .sum();
        model.addAttribute("todayPayments", todayPayments);

        // Completed vs pending appointments (assuming Appointment has getStatus() or similar)
        long completedAppointments = appointments.stream()
                .filter(a -> "COMPLETED".equalsIgnoreCase(a.getStatus()))
                .count();
        long pendingAppointments = appointments.size() - completedAppointments;
        model.addAttribute("completedAppointments", completedAppointments);
        model.addAttribute("pendingAppointments", pendingAppointments);

        return "dashboard";
    }
}
