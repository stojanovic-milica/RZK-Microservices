package com.rzk.terminservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "recenzija", schema = "servis_termini")
public class Recenzija {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull(message = "Ocena je obavezna")
    @Min(value = 1, message = "Minimalna ocena je 1")
    @Max(value = 5, message = "Maksimalna ocena je 5")
    @Column(name = "ocena")
    private Integer ocena;

    @NotBlank(message = "Komentar ne moze biti prazan")
    @Lob
    @Column(name = "komentar")
    private String komentar;

    @Column(name = "klijent_id")
    private Integer klijentId;

    @NotNull(message = "ID usluge je obavezan")
    @Column(name = "usluga_id")
    private Integer uslugaId;

}