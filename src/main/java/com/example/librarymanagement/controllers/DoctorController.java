package com.example.librarymanagement.controllers;

import com.example.librarymanagement.dto.DoctorDto;
import com.example.librarymanagement.model.Doctor;
import com.example.librarymanagement.service.DoctorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class DoctorController {

    private final DoctorService service;

    public DoctorController(DoctorService service) {
        this.service = service;
    }

    @GetMapping("doctor")
    public String getDoctor(Model model) {
        model.addAttribute("dto", new DoctorDto("", "", "", "", "", 0.0));

        List<Doctor> doctorList = service.getALL();
        model.addAttribute("doctors", doctorList);

        return "doctor";
    }

    @PostMapping("doctor")
    public String addDoctor(@ModelAttribute DoctorDto dto) {
        System.out.println("Test: " + dto);

        Doctor doctor = service.save(dto.toDoctor());
        if (doctor == null) {
            return "redirect:doctor?doctor=failed";
        } else {
            System.out.println("Added new doctor");
            return "redirect:doctor";
        }
    }

    @PostMapping("doctor/{email}/delete")
    public String deleteDoctor(@PathVariable String email) {
        if (service.hasNoChild(email)) {
            service.delete(email);
            return "redirect:/doctor";
        }
        return "redirect:/doctor?child=true";
    }

    @PostMapping("doctor/update")
    public String updateDoctor(@ModelAttribute DoctorDto dto) {
        service.update(dto.toDoctor());
        return "redirect:/doctor";
    }
}