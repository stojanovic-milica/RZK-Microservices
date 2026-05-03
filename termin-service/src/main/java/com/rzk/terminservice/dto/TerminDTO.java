package com.rzk.terminservice.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TerminDTO {

    private Integer id;

//    @NotNull(message = "ID klijenta je obavezan")
    private Integer klijentId;

    private Integer zaposleniId;


    @NotNull(message = "Datum je obavezan")
    @FutureOrPresent(message = "Datum ne moze biti u proslosti")
    private LocalDate datum;

    @NotNull(message = "Satnica je obavezna")
    @Min(value = 8, message = "Radno vreme pocinje od 08h")
    @Max(value = 20, message = "Radno vreme se zavrsava u 20h")
    private Integer sat;

    @NotEmpty(message = "Morate izabrati barem jednu uslugu")
    private List<Integer> uslugaIds = new ArrayList<>();

    private String status;

}
