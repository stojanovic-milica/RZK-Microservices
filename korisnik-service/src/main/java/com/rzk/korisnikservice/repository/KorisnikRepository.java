package com.rzk.korisnikservice.repository;

import com.rzk.korisnikservice.model.Korisnik;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KorisnikRepository extends JpaRepository<Korisnik, Integer> {

    Optional<Korisnik> findByEmail (String email);
}