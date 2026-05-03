package com.rzk.edukacijaservice.repository;

import com.rzk.edukacijaservice.model.Novost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NovostRepository extends JpaRepository<Novost, Integer> {

    List<Novost> findAllByOrderByDatumObjaveDesc();

}