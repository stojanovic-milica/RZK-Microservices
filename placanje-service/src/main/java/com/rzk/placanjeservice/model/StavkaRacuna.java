package com.rzk.placanjeservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "stavka_racuna", schema = "servis_placanje")
public class StavkaRacuna {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "racun_id")
    private Racun racun;

    @Column(name = "termin_id")
    private Integer terminId;

    @Column(name = "naziv_usluge")
    private String nazivUsluge;

    @Column(name = "cena_usluge")
    private Double cenaUsluge;

}