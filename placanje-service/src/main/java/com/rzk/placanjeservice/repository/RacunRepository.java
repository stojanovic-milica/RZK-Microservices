package com.rzk.placanjeservice.repository;

import com.rzk.placanjeservice.model.Racun;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RacunRepository extends JpaRepository<Racun, Integer> {

    boolean existsByTerminId(Integer terminId);

    @Query("SELECT r.terminId FROM Racun r")
    List<Integer> findAllTerminIdsSaRacunom();

}