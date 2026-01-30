package com.example.librarymanagement.config;

import com.example.librarymanagement.util.IO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filter(HttpSecurity http) throws Exception {
        IO.println("Security Configured");

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(request -> request
                        // Static resources - permit all (MUST be first)
                        .requestMatchers("/css/**", "/js/**", "/img/**", "/images/**", "/vendor/**", "/fonts/**").permitAll()
                        .requestMatchers("/favicon.ico", "/favicon/**", "/*.ico").permitAll()
                        
                        // Public pages - no authentication required
                        .requestMatchers("/login", "/register", "/registration", "/forget").permitAll()
                        
                        // Dashboard - any authenticated user
                        .requestMatchers("/", "/dashboard").authenticated()
                        
                        // Admin only pages
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        
                        // Doctor and Admin can access doctor management
                        .requestMatchers("/doctor", "/doctor/**").hasAnyRole("ADMIN", "DOCTOR")
                        
                        // Admin and Doctor can manage patients
                        .requestMatchers("/patient", "/patient/**").hasAnyRole("ADMIN", "DOCTOR")
                        
                        // Appointment - all roles can access but with different views
                        .requestMatchers("/appointment", "/appointment/**").hasAnyRole("ADMIN", "DOCTOR", "PATIENT")
                        
                        // Payment - Admin and Doctor only, requires full authentication
                        .requestMatchers("/payment", "/payment/**").hasAnyRole("ADMIN", "DOCTOR")
                        
                        // Change password - any authenticated user
                        .requestMatchers("/change-password").authenticated()
                        
                        // Any other request requires authentication
                        .anyRequest().authenticated()
                )

                .formLogin(form -> form
                        .loginPage("/login")
                        .usernameParameter("mobile")
                        .passwordParameter("pin")
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/login?error")
                        .permitAll())

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID", "remember")
                )

                .rememberMe(rm -> rm
                        .rememberMeParameter("remember")
                        .rememberMeCookieName("remember-cookie")
                        .tokenValiditySeconds(9000)
                )

        ;

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> User.withUsername(username).password(" ").build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
