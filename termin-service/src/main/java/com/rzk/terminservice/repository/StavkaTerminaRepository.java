package com.rzk.terminservice.repository;

import com.rzk.terminservice.model.StavkaTermina;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StavkaTerminaRepository extends JpaRepository<StavkaTermina, Integer> {

    List<StavkaTermina> findByUslugaId(int id);

}