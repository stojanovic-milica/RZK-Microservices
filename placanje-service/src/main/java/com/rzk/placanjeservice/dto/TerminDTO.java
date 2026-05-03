package com.rzk.placanjeservice.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TerminDTO {

    private Integer id;

    private Integer klijentId;

//    @com.fasterxml.jackson.annotation.JsonProperty("zaposleniId")
    private Integer zaposleniId;

    private LocalDate datum;

    private Integer sat;

    private List<Integer> uslugaIds;

    private String status;


}
