package com.rzk.placanjeservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "racun", schema = "servis_placanje")
public class Racun {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "klijent_id")
    private Integer klijentId;

    @Column(name = "termin_id")
    private Integer terminId;

    @ManyToOne
    @JoinColumn(name = "popust_id")
    private Popust popust;

    @Column(name = "popust_procenat")
    private Integer popustProcenat;

    @Column(name = "ukupna_cena_pre")
    private Double ukupnaCenaPre;

    @Column(name = "ukupna_cena_posle")
    private Double ukupnaCenaPosle;

    @Column(name = "datum_izdavanja")
    private LocalDate datumIzdavanja;

    @OneToMany(mappedBy = "racun")
    private Set<StavkaRacuna> stavkaRacunas = new LinkedHashSet<>();

}