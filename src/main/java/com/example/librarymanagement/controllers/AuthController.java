package com.example.librarymanagement.controllers;

import com.example.librarymanagement.config.CustomAuthenticationProvider;
import com.example.librarymanagement.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    private final PasswordEncoder passwordEncoder;

    public AuthController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid mobile number or PIN");
        }
        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully");
        }
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @GetMapping("/registration")
    public String registrationPage() {
        return "register";
    }

    @PostMapping("/register")
    public String processRegistration(@RequestParam String name,
                                       @RequestParam String mobile,
                                       @RequestParam(required = false) String email,
                                       @RequestParam String pin,
                                       @RequestParam String confirmPin,
                                       @RequestParam String role) {
        // Validate PIN match
        if (!pin.equals(confirmPin)) {
            return "redirect:/register?error=pinMismatch";
        }

        // Check if mobile already exists
        if (CustomAuthenticationProvider.userExists(mobile)) {
            return "redirect:/register?error=exists";
        }

        // Create and register the user
        User newUser = new User(name, mobile, email, passwordEncoder.encode(pin), role.toUpperCase());
        CustomAuthenticationProvider.registerUser(newUser);

        return "redirect:/login?registered";
    }

    @GetMapping("/forget")
    public String forgetPasswordPage() {
        return "forget-password";
    }

    @PostMapping("/forget")
    public String processForgetPassword(@RequestParam String mobile) {
        // Check if mobile exists
        if (!CustomAuthenticationProvider.userExists(mobile)) {
            return "redirect:/forget?error";
        }
        // TODO: Send reset link/OTP to mobile
        return "redirect:/forget?success";
    }

    @GetMapping("/change-password")
    public String changePasswordPage() {
        return "change-password";
    }

    @PostMapping("/change-password")
    public String processChangePassword(@RequestParam String currentPin,
                                         @RequestParam String newPin,
                                         @RequestParam String confirmNewPin,
                                         java.security.Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        String mobile = principal.getName();
        User user = CustomAuthenticationProvider.getUser(mobile);

        if (user == null) {
            return "redirect:/login";
        }

        // Validate current PIN
        if (!passwordEncoder.matches(currentPin, user.getPassword())) {
            return "redirect:/change-password?error";
        }

        // Validate new PIN match
        if (!newPin.equals(confirmNewPin)) {
            return "redirect:/change-password?mismatch";
        }

        // Update PIN
        user.setPassword(passwordEncoder.encode(newPin));

        return "redirect:/?passwordChanged";
    }
}
