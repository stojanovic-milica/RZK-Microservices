package com.rzk.korisnikservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "kartica_lojalnosti", schema = "servis_korisnik")
public class KarticaLojalnosti {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "korisnik_id")
    private Korisnik korisnik;

    @Column(name = "poeni")
    private Integer poeni;


}