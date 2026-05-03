package com.rzk.terminservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "usluga", schema = "servis_termini")
public class Usluga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "naziv")
    private String naziv;

    @Lob
    @Column(name = "opis")
    private String opis;

    @Column(name = "cena")
    private Double cena;


}