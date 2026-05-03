package com.rzk.korisnikservice.service;

import com.rzk.korisnikservice.dto.KorisnikDTO;
import com.rzk.korisnikservice.dto.PrijavaDTO;
import com.rzk.korisnikservice.exception.KorisnickiEmailZauzetException;
import com.rzk.korisnikservice.exception.NeuspesnaPrijavaException;
import com.rzk.korisnikservice.model.Korisnik;
import com.rzk.korisnikservice.repository.KorisnikRepository;
import com.rzk.korisnikservice.repository.UlogaRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class KorisnikService {

    private KorisnikRepository korisnikRepository;
    private UlogaRepository ulogaRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    public Korisnik getKorisnikByEmail (String email) {
        Optional<Korisnik> korisnikOptional = korisnikRepository.findByEmail(email);

        if (korisnikOptional.isEmpty()) {
            return null;
        }

        return korisnikOptional.get();
    }

    public Korisnik getKorisnikById(int id) {
        Optional<Korisnik> korisnikOptional = korisnikRepository.findById(id);

        if (korisnikOptional.isEmpty()) {
            return null;
        }

        return korisnikOptional.get();
    }


    public Korisnik registrujKorisnika(KorisnikDTO dto) {

        if (korisnikRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new KorisnickiEmailZauzetException("Greska: Korisnik sa ovim emailom vec postoji!");
        }


        Korisnik noviKorisnik = new Korisnik();
        noviKorisnik.setIme(dto.getIme());
        noviKorisnik.setPrezime(dto.getPrezime());
        noviKorisnik.setEmail(dto.getEmail());
        noviKorisnik.setLozinka(passwordEncoder.encode(dto.getLozinka()));
        noviKorisnik.setTelefon(dto.getTelefon());
        noviKorisnik.setUloga(ulogaRepository.findById(2).get());

        return korisnikRepository.save(noviKorisnik);
    }

    public Korisnik prijavaKorisnika(PrijavaDTO prijavaDTO) {
        Korisnik korisnik = korisnikRepository.findByEmail(prijavaDTO.getEmail())
                .orElseThrow(() -> new NeuspesnaPrijavaException("Greska: Korisnik sa ovim emailom ne postoji!"));

        if (!passwordEncoder.matches(prijavaDTO.getLozinka(), korisnik.getLozinka())) {
            throw new NeuspesnaPrijavaException("Greska: Pogresna lozinka!");
        }

        return korisnik;
    }

}