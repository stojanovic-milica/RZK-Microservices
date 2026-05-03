package com.rzk.edukacijaservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class KorisnikDTO {

    private Integer id;

    private String ime;

    private String prezime;

    private String email;

    private String lozinka;

    private String telefon;

    private UlogaDTO uloga;

}