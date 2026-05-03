package com.rzk.terminservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String adminEmail;

    public void posaljiMejlPotvrde(String klijentEmail, String datum, String sat) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom(adminEmail);
            message.setTo(klijentEmail);
            message.setSubject("Potvrda termina - Salon lepote");

            String tekstPoruke = String.format(
                    "Postovani,\n\nVas termin je uspešno zakazan za %s u %s h.\nRadujemo se Vasem dolasku!\n\nVas Salon.",
                    datum, sat
            );

            message.setText(tekstPoruke);

            mailSender.send(message);
            System.out.println("Sistem: Mejl uspesno poslat na adresu: " + klijentEmail);

        } catch (Exception e) {
            System.err.println("GRESKA: Slanje mejla nije uspelo! Razlog: " + e.getMessage());
        }
    }
}