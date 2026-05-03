package com.rzk.placanjeservice.repository;

import com.rzk.placanjeservice.model.Popust;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface PopustRepository extends JpaRepository<Popust, Integer> {

    Optional<Popust> findByDatumVazenja (LocalDate datum);

}