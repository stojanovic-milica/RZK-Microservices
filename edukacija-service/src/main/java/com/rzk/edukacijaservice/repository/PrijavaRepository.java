package com.rzk.edukacijaservice.repository;

import com.rzk.edukacijaservice.model.Edukacija;
import com.rzk.edukacijaservice.model.Prijava;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface PrijavaRepository extends JpaRepository<Prijava, Integer> {

    int countByEdukacijaId (int edukacijaId);

    boolean existsByEdukacijaIdAndKlijentId (int edukacijaId, int klijentId);

    List<Prijava> findByKlijentId (int klijentId);

    void deleteByEdukacijaId (int edukacijaId);

    void deleteByEdukacijaIdAndKlijentId (int edukacijaId, int klijentId);

    List<Prijava> findByEdukacijaId (int edukacijaId);
}