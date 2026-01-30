package com.example.librarymanagement.config;

import com.example.librarymanagement.model.User;
import com.example.librarymanagement.util.IO;
import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    // Store users with their details
    public final static HashMap<String, User> userMap = new HashMap<>();

    public final PasswordEncoder passwordEncoder;

    public CustomAuthenticationProvider(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
        
        // Default admin user
        userMap.put("123", new User("Admin", "123", "admin@hospital.com",
                passwordEncoder.encode("123"), "ADMIN"));
        
        // Default doctor user
        userMap.put("456", new User("Dr. Smith", "456", "doctor@hospital.com",
                passwordEncoder.encode("456"), "DOCTOR"));
        
        // Default patient user
        userMap.put("789", new User("John Doe", "789", "patient@hospital.com",
                passwordEncoder.encode("789"), "PATIENT"));
    }

    @Override
    public @Nullable Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String mobile = authentication.getName();
        String rawPassword = authentication.getCredentials().toString();
        IO.println(mobile + " " + rawPassword);

        User user = userMap.get(mobile);
        
        if (user == null) {
            throw new BadCredentialsException("Invalid mobile or password");
        }

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new BadCredentialsException("Invalid mobile or password");
        }

        // Create authorities based on role
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
        
        // Add specific permissions based on role
        switch (user.getRole()) {
            case "ADMIN":
                authorities.add(new SimpleGrantedAuthority("READ_INVOICE"));
                authorities.add(new SimpleGrantedAuthority("READ_REPORT"));
                authorities.add(new SimpleGrantedAuthority("MANAGE_USERS"));
                authorities.add(new SimpleGrantedAuthority("MANAGE_DOCTORS"));
                authorities.add(new SimpleGrantedAuthority("MANAGE_PATIENTS"));
                break;
            case "DOCTOR":
                authorities.add(new SimpleGrantedAuthority("READ_REPORT"));
                authorities.add(new SimpleGrantedAuthority("MANAGE_APPOINTMENTS"));
                authorities.add(new SimpleGrantedAuthority("VIEW_PATIENTS"));
                break;
            case "PATIENT":
                authorities.add(new SimpleGrantedAuthority("BOOK_APPOINTMENT"));
                authorities.add(new SimpleGrantedAuthority("VIEW_OWN_RECORDS"));
                break;
        }

        return new UsernamePasswordAuthenticationToken(mobile, null, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
    
    /**
     * Register a new user
     */
    public static void registerUser(User user) {
        userMap.put(user.getMobile(), user);
    }
    
    /**
     * Get user by mobile
     */
    public static User getUser(String mobile) {
        return userMap.get(mobile);
    }
    
    /**
     * Check if user exists
     */
    public static boolean userExists(String mobile) {
        return userMap.containsKey(mobile);
    }
}
