package com.rzk.terminservice.repository;

import com.rzk.terminservice.dto.UslugaStatistikaDTO;
import com.rzk.terminservice.model.Recenzija;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecenzijaRepository extends JpaRepository<Recenzija, Integer> {

    @Query("SELECT new com.rzk.terminservice.dto.UslugaStatistikaDTO(r.uslugaId, AVG(r.ocena)) " +
            "FROM Recenzija r GROUP BY r.uslugaId")
    List<UslugaStatistikaDTO> getSirovaStatistika ();

    List<Recenzija> findByUslugaId(int id);

}