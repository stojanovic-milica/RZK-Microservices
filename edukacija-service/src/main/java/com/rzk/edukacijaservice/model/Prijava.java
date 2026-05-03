package com.rzk.edukacijaservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "prijava", schema = "servis_edukacija")
public class Prijava {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "edukacija_id")
    private Edukacija edukacija;

    @Column(name = "klijent_id")
    private Integer klijentId;

    @Column(name = "datum_prijave")
    private LocalDate datumPrijave;

}