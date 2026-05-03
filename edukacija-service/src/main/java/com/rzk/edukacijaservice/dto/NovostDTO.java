package com.rzk.edukacijaservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class NovostDTO {

    @NotBlank(message = "Naslov vesti je obavezan")
    @Size(min = 3, message = "Naslov mora imati bar 3 karaktera")
    private String naslov;

    @NotBlank(message = "Sadrzaj vesti ne sme biti prazan")
    private String sadrzaj;

}
