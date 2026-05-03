package com.rzk.edukacijaservice.repository;

import com.rzk.edukacijaservice.model.Edukacija;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EdukacijaRepository extends JpaRepository<Edukacija, Integer> {

    List<Edukacija> findByDatumAfter (LocalDate date);

}