package com.example.librarymanagement.controllers;

import com.example.librarymanagement.dto.PatientDto;
import com.example.librarymanagement.model.Patient;
import com.example.librarymanagement.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PatientController {

    private final PatientService service;

    public PatientController(PatientService service) {
        this.service = service;
    }

    @GetMapping("patient")
    public String getPatient(Model model) {
        model.addAttribute("dto", new PatientDto("", "", "", "", "", ""));

        List<Patient> patientList = service.getALL();
        model.addAttribute("patients", patientList);

        return "patient";
    }

    @PostMapping("patient")
    public String addPatient(@ModelAttribute PatientDto dto) {
        System.out.println("Test: " + dto);

        Patient patient = service.save(dto.toPatient());
        if (patient == null) {
            return "redirect:patient?patient=failed";
        } else {
            System.out.println("Added new patient");
            return "redirect:patient";
        }
    }

    @PostMapping("patient/{email}/delete")
    public String deletePatient(@PathVariable String email) {
        if (service.hasNoChild(email)) {
            service.delete(email);
            return "redirect:/patient";
        }
        return "redirect:/patient?child=true";
    }

    @PostMapping("patient/update")
    public String updatePatient(@ModelAttribute PatientDto dto) {
        service.update(dto.toPatient());
        return "redirect:/patient";
    }
}