package com.example.librarymanagement.controllers;

import com.example.librarymanagement.dto.AppointmentDto;
import com.example.librarymanagement.model.Appointment;
import com.example.librarymanagement.model.Doctor;
import com.example.librarymanagement.model.Patient;
import com.example.librarymanagement.service.AppointmentService;
import com.example.librarymanagement.service.DoctorService;
import com.example.librarymanagement.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final DoctorService doctorService;
    private final PatientService patientService;

    public AppointmentController(AppointmentService appointmentService,
                                 DoctorService doctorService,
                                 PatientService patientService) {
        this.appointmentService = appointmentService;
        this.doctorService = doctorService;
        this.patientService = patientService;
    }

    @GetMapping("appointment")
    public String getAppointment(Model model) {
        model.addAttribute("dto", new AppointmentDto(null, null, "", "", "", ""));

        List<Appointment> appointmentList = appointmentService.getALL();
        model.addAttribute("appointments", appointmentList);

        List<Doctor> doctorList = doctorService.getALL();
        model.addAttribute("doctors", doctorList);

        List<Patient> patientList = patientService.getALL();
        model.addAttribute("patients", patientList);

        return "appointment";
    }

    @PostMapping("appointment")
    public String addAppointment(@ModelAttribute AppointmentDto dto) {
        System.out.println("Test: " + dto);

        appointmentService.save(dto.toAppointment());
        return "redirect:appointment";
    }

    @PostMapping("appointment/{id}/delete")
    public String deleteAppointment(@PathVariable Integer id) {
        appointmentService.delete(id);
        return "redirect:/appointment";
    }

    @PostMapping("appointment/{id}/complete")
    public String completeAppointment(@PathVariable Integer id) {
        Appointment appointment = appointmentService.getById(id);
        if (appointment != null) {
            appointment.setStatus("COMPLETED");
            appointmentService.update(appointment);
        }
        return "redirect:/appointment";
    }

    @PostMapping("appointment/{id}/cancel")
    public String cancelAppointment(@PathVariable Integer id) {
        Appointment appointment = appointmentService.getById(id);
        if (appointment != null) {
            appointment.setStatus("CANCELLED");
            appointmentService.update(appointment);
        }
        return "redirect:/appointment";
    }
}