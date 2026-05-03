package com.rzk.terminservice.repository;

import com.rzk.terminservice.model.Termin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TerminRepository extends JpaRepository<Termin, Integer> {

    List<Termin> findByStatusOrderByDatumAscSatAsc (String status);

    List<Termin> findByKlijentIdAndDatumGreaterThanEqualOrderByDatumAscSatAsc (Integer klijentId, LocalDate danasnjiDatum);

    List<Termin> findByStatus(String status);

}