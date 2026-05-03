package com.rzk.terminservice.dto;

import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UslugaStatistikaDTO {

    private Integer uslugaId;
    private String naziv;
    private Double prosecnaOcena;

    public UslugaStatistikaDTO(Integer uslugaId, Double prosecnaOcena) {
        this.uslugaId = uslugaId;
        this.prosecnaOcena = prosecnaOcena;
    }

}
