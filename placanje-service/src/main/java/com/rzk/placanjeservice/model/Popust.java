package com.rzk.placanjeservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "popust", schema = "servis_placanje")
public class Popust {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "naziv_akcije")
    private String nazivAkcije;

    @Column(name = "procenat")
    private Integer procenat;

    @Column(name = "datum_vazenja")
    private LocalDate datumVazenja;

    @JsonIgnore
    @OneToMany(mappedBy = "popust")
    private Set<Racun> racuns = new LinkedHashSet<>();

}