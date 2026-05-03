package com.rzk.edukacijaservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "edukacija", schema = "servis_edukacija")
public class Edukacija {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "naziv")
    private String naziv;

    @Lob
    @Column(name = "opis")
    private String opis;

    @Column(name = "cena", precision = 10, scale = 2)
    private BigDecimal cena;

    @Column(name = "max_polaznika")
    private Integer maxPolaznika;

    @Column(name = "predavac_id")
    private Integer predavacId;

    @Column(name = "datum")
    private LocalDate datum;

    @Column(name = "sat")
    private Integer sat;

    @JsonIgnore
    @OneToMany(mappedBy = "edukacija")
    private Set<Prijava> prijavas = new LinkedHashSet<>();


}