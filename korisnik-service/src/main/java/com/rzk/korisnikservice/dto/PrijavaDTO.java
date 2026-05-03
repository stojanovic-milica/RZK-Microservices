package com.rzk.korisnikservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PrijavaDTO {

    @Email(message = "Email mora biti u ispravnom formatu")
    @NotBlank(message = "Email je obavezan")
    private String email;

//    @JsonIgnore
    @NotBlank(message = "Lozinka je obavezna")
    private String lozinka;

}
