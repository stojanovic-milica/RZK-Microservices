package com.rzk.korisnikservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "korisnik", schema = "servis_korisnik")
public class Korisnik {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "ime")
    private String ime;

    @Column(name = "prezime")
    private String prezime;

    @Column(name = "email")
    private String email;

    @JsonIgnore
    @Column(name = "lozinka")
    private String lozinka;

    @Column(name = "telefon")
    private String telefon;

    @ManyToOne
    @JoinColumn(name = "uloga_id")
    private Uloga uloga;

    @OneToOne(mappedBy = "korisnik")
    private KarticaLojalnosti karticaLojalnosti;


}