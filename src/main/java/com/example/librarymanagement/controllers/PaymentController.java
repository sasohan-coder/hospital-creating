package com.example.librarymanagement.controllers;

import com.example.librarymanagement.dto.PaymentDto;
import com.example.librarymanagement.model.Appointment;
import com.example.librarymanagement.model.Patient;
import com.example.librarymanagement.model.Payment;
import com.example.librarymanagement.service.AppointmentService;
import com.example.librarymanagement.service.PatientService;
import com.example.librarymanagement.service.PaymentService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
public class PaymentController {

    private final PaymentService paymentService;
    private final PatientService patientService;
    private final AppointmentService appointmentService;

    public PaymentController(PaymentService paymentService,
                             PatientService patientService,
                             AppointmentService appointmentService) {
        this.paymentService = paymentService;
        this.patientService = patientService;
        this.appointmentService = appointmentService;
    }

    @GetMapping("payment")
    public String getPayment(Model model,
                             @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate filterDate) {
        model.addAttribute("dto", new PaymentDto(null, null, "", "", "", "", "", ""));

        List<Payment> paymentList;
        Double totalAmount;

        if (filterDate != null) {
            paymentList = paymentService.getByDate(filterDate);
            totalAmount = paymentService.getTotalPaymentsByDate(filterDate);
            model.addAttribute("filterDate", filterDate);
        } else {
            paymentList = paymentService.getAll();
            totalAmount = paymentService.getTotalPayments();
        }

        model.addAttribute("payments", paymentList);
        model.addAttribute("totalAmount", totalAmount);

        List<Patient> patientList = patientService.getALL();
        model.addAttribute("patients", patientList);

        List<Appointment> appointmentList = appointmentService.getALL();
        model.addAttribute("appointments", appointmentList);

        return "payment";
    }

    @PostMapping("payment")
    public String addPayment(@ModelAttribute PaymentDto dto) {
        System.out.println("Payment: " + dto);
        paymentService.save(dto.toPayment());
        return "redirect:payment";
    }

    @PostMapping("payment/{id}/delete")
    public String deletePayment(@PathVariable Integer id) {
        paymentService.delete(id);
        return "redirect:/payment";
    }

    @GetMapping("payment/search")
    public String searchPaymentByDate(Model model,
                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Payment> paymentList = paymentService.getByDateRange(startDate, endDate);
        model.addAttribute("payments", paymentList);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        return "payment";
    }
}