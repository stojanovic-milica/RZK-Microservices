package com.rzk.korisnikservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class KorisnikDTO {

    private Integer id;

    @NotBlank(message = "Ime je obavezno")
    @Size(min = 3, max = 15)
    private String ime;

    @NotBlank(message = "Prezime je obavezno")
    @Size(min = 3, max = 15)
    private String prezime;

    @Email(message = "Email mora biti u ispravnom formatu")
    @Email(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",
            message = "Email format nije ispravan")
    @NotBlank(message = "Email je obavezan")
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 6, message = "Lozinka mora imati barem 6 karaktera")
    private String lozinka;

    @Pattern(
            regexp = "^(06[0-9]{7,8}|\\+381[0-9]{8,9})$",
            message = "Broj telefona mora poceti sa 06 (ukupno 9-10 cifara) ili +381 (ukupno 11-12 karaktera)"
    )
    private String telefon;

//    @NotNull(message = "Uloga mora biti definisana")
    private Integer uloga;

}