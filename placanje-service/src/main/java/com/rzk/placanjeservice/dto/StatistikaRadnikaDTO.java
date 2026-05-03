package com.rzk.placanjeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatistikaRadnikaDTO {

    private Integer zaposleniId;
    private double ukupnaZarada;
    private double procenat;

}