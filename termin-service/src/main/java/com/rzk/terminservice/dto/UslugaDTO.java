package com.rzk.terminservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@NoArgsConstructor
@AllArgsConstructor
public class UslugaDTO {

    @NotBlank(message = "Naziv usluge ne sme biti prazan")
    @Size(min = 3, max = 50, message = "Naziv mora imati izmedju 3 i 50 karaktera")
    private String naziv;

    @Size(max = 255, message = "Opis ne sme biti duzi od 255 karaktera")
    private String opis;

    @NotNull(message = "Cena je obavezna")
    @Min(value = 0, message = "Cena ne moze biti negativna")
    private Double cena;

}