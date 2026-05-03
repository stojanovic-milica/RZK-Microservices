package com.rzk.placanjeservice.dto;

import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
@Setter
public class RacunDTO {

    @NotNull
    private Integer terminId;

    @NotNull
    private Integer klijentId;

    private String nazivAkcije;

}
