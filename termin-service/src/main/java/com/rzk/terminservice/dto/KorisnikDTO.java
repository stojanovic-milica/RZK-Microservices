package com.rzk.terminservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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