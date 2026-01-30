package com.example.librarymanagement.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PublicController {

    @GetMapping("/contact-us")
    public String contactUsPage() {
        return "contact-us";
    }

    @GetMapping("/about-us")
    public String aboutUsPage() {
        return "about-us";
    }
}
