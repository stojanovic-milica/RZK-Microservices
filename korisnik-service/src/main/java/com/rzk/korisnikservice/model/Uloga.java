package com.rzk.korisnikservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "uloga", schema = "servis_korisnik")
public class Uloga {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "naziv_uloge")
    private String nazivUloge;

    @OneToMany(mappedBy = "uloga")
    @JsonIgnore
    private Set<Korisnik> korisniks = new LinkedHashSet<>();


}