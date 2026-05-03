package com.rzk.edukacijaservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "novost", schema = "servis_edukacija")
public class Novost {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "naslov")
    private String naslov;

    @Lob
    @Column(name = "sadrzaj")
    private String sadrzaj;

    @Column(name = "datum_objave")
    private LocalDate datumObjave;


}