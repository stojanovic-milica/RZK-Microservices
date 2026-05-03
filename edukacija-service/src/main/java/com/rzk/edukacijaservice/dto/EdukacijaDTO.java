package com.rzk.edukacijaservice.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.swing.plaf.basic.BasicIconFactory;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class EdukacijaDTO {

    @NotBlank(message = "Naziv je obavezan")
    private String naziv;

    private String opis;

    @NotNull(message = "Cena je obavezna")
    @Min(value = 0, message = "Cenam mora biti veca od 0")
    private BigDecimal cena;

    @NotNull(message = "Max polaznika je obavezan")
    private Integer maxPolaznika;

    @NotNull(message = "ID predavaca je obavezan")
    private Integer predavacId;

    @NotNull(message = "Datum je obavezan")
    @Future(message = "Datum edukacije mora biti u buducnosti")
    private LocalDate datum;

    @NotNull(message = "Sat je obavezan")
    @Min(value = 8, message = "Radno vreme pocinje od 08h")
    @Max(value = 20, message = "Radno vreme se zavrsava u 20h")
    private Integer sat;
}