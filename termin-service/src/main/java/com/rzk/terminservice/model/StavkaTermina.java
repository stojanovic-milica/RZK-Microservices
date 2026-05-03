package com.rzk.terminservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "stavka_termina", schema = "servis_termini")
public class StavkaTermina {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "termin_id")
    private Termin termin;

    @Column(name = "usluga_id")
    private Integer uslugaId;

    @Transient
    private String nazivUsluge;


}