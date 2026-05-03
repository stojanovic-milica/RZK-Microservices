package com.rzk.korisnikservice.repository;

import com.rzk.korisnikservice.model.Uloga;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UlogaRepository extends JpaRepository<Uloga, Integer> {
}