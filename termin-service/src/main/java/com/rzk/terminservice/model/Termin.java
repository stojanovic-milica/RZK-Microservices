package com.rzk.terminservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "termin", schema = "servis_termini")
public class Termin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "klijent_id")
    private Integer klijentId;

    @Column(name = "zaposleni_id")
    private Integer zaposleniId;

    @Column(name = "datum")
    private LocalDate datum;

    @Column(name = "sat")
    private Integer sat;

    @Column(name = "status")
    private String status;

//    @OneToMany(mappedBy = "termin")
    @OneToMany(mappedBy = "termin", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<StavkaTermina> stavkaTerminas = new LinkedHashSet<>();


}