package com.rzk.terminservice.service;

import com.rzk.terminservice.dto.KorisnikDTO;
import com.rzk.terminservice.dto.TerminDTO;
import com.rzk.terminservice.exception.*;
import com.rzk.terminservice.model.StavkaTermina;
import com.rzk.terminservice.model.Termin;
import com.rzk.terminservice.model.Usluga;
import com.rzk.terminservice.proxy.KorisnikProxy;
import com.rzk.terminservice.repository.StavkaTerminaRepository;
import com.rzk.terminservice.repository.TerminRepository;
import com.rzk.terminservice.repository.UslugaRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class TerminService {

    private final TerminRepository terminRepository;
    private final UslugaRepository uslugaRepository;
    private final StavkaTerminaRepository stavkaTerminaRepository;

    private final EmailService emailService;

    private final KorisnikProxy korisnikProxy;

    public List<Termin> getBuduciTerminiKlijenta(Integer klijentId) {
        KorisnikDTO kor;
        try {
            kor = korisnikProxy.getKorisnikById(klijentId).getBody();
        }
        catch (Exception ex) {
            throw new KorisnikNotFoundException("Korisnik sa ID-jem " + klijentId + " ne postoji u sistemu korisnika!");
        }

        if (kor.getUloga().getId() != 2) {
            throw new SamoZaKorisnikeException("Samo klijenti se mogu imati termine!");
        }

        LocalDate danas = LocalDate.now();

        List<Termin> buduci = terminRepository
                .findByKlijentIdAndDatumGreaterThanEqualOrderByDatumAscSatAsc(klijentId, danas);

        for (Termin t : buduci) {
            for (StavkaTermina stavka : t.getStavkaTerminas()) {
                Usluga u = uslugaRepository.findById(stavka.getUslugaId()).orElse(null);
                if (u != null) {
                    stavka.setNazivUsluge(u.getNaziv());
                }
            }
        }
        return buduci;
    }

    @Transactional
    public Termin zakaziNoviTermin(TerminDTO dto, Integer klijentId) {
        KorisnikDTO kor;
        try {
            kor = korisnikProxy.getKorisnikById(klijentId).getBody();
        }
        catch (Exception ex) {
            throw new KorisnikNotFoundException("Korisnik sa ID-jem " + klijentId + " ne postoji u sistemu korisnika!");
        }

        if (kor.getUloga().getId() != 2) {
            throw new SamoZaKorisnikeException("Samo klijenti se mogu zakazivati termine!");
        }

        Termin termin = new Termin();
        termin.setKlijentId(klijentId);
        termin.setDatum(dto.getDatum());
        termin.setSat(dto.getSat());
        termin.setStatus("NA_CEKANJU");

        for (Integer uId : dto.getUslugaIds()) {
            Usluga u = uslugaRepository.findById(uId)
                    .orElseThrow(() -> new UslugaNotFoundException("Usluga " + uId + " ne postoji."));

            StavkaTermina stavka = new StavkaTermina();
            stavka.setTermin(termin);
            stavka.setUslugaId(uId);
            stavka.setNazivUsluge(u.getNaziv());

            termin.getStavkaTerminas().add(stavka);
        }

        return terminRepository.save(termin);
    }

    @Transactional
    public Termin otkaziSvojTermin (Integer terminId, Integer klijentId) {
        KorisnikDTO kor;
        try {
            kor = korisnikProxy.getKorisnikById(klijentId).getBody();
        }
        catch (Exception ex) {
            throw new KorisnikNotFoundException("Korisnik sa ID-jem " + klijentId + " ne postoji u sistemu korisnika!");
        }


        if (kor.getUloga().getId() != 2) {
            throw new SamoZaKorisnikeException("Samo klijenti se mogu imati termine!");
        }

        Termin termin = terminRepository.findById(terminId)
                .orElseThrow(() -> new TerminNotFoundException("Termin sa ID-em " + terminId + " nije pronadjen."));

        if (!termin.getKlijentId().equals(klijentId)) {
            throw new TerminNijeVasException("Greska: Ne mozete otkazati termin koji nije vas!");
        }

        termin.setStatus("OTKAZAN");

        return terminRepository.save(termin);
    }



    // ZAPOSLENI

    public List<TerminDTO> getPotvrdjeniTermini() {
        List<Termin> potvrdjeni = terminRepository.findByStatus("POTVRDJEN");

        return potvrdjeni.stream().map(t -> {
            TerminDTO dto = new TerminDTO();
            dto.setId(t.getId());
            dto.setKlijentId(t.getKlijentId());
            dto.setDatum(t.getDatum());
            dto.setSat(t.getSat());
            dto.setStatus(t.getStatus());

            for (StavkaTermina stavka : t.getStavkaTerminas()) {
                dto.getUslugaIds().add(stavka.getUslugaId());
            }
            return dto;
        }).toList();
    }

    public TerminDTO getTerminDTO (Integer id) {
        Termin termin = terminRepository.findById(id)
                .orElseThrow(() -> new TerminNotFoundException("Termin sa ID " + id + " nije pronadjen!"));

        TerminDTO dto = new TerminDTO();
        dto.setId(termin.getId());
        dto.setKlijentId(termin.getKlijentId());
        dto.setZaposleniId(termin.getZaposleniId());
        dto.setDatum(termin.getDatum());
        dto.setSat(termin.getSat());

        for (StavkaTermina sta : termin.getStavkaTerminas()) {
            Integer uslugaId = sta.getUslugaId();
            dto.getUslugaIds().add(uslugaId);
        }

        return dto;
    }

    public Termin getTermin (Integer id) {
        Termin termin = terminRepository.findById(id)
                .orElseThrow(() -> new TerminNotFoundException("Termin sa ID " + id + " nije pronadjen!"));

        for (StavkaTermina sta : termin.getStavkaTerminas()) {
            Usluga u = uslugaRepository.findById(sta.getUslugaId())
                    .orElseThrow(() -> new UslugaNotFoundException("Usluga " + sta.getUslugaId() + " ne postoji."));

            sta.setNazivUsluge(u.getNaziv());

        }

        return termin;
    }

    public List<Termin> getSlobodniTermini() {
        List<Termin> slobodni = terminRepository.findByStatusOrderByDatumAscSatAsc("NA_CEKANJU");

        for (Termin t : slobodni) {
            for (StavkaTermina stavka : t.getStavkaTerminas()) {
                Usluga u = uslugaRepository.findById(stavka.getUslugaId()).orElse(null);
                if (u != null) {
                    stavka.setNazivUsluge(u.getNaziv());
                }
            }
        }
        return slobodni;
    }

    @Transactional
    public Termin prihvatiTermin (Integer terminId, Integer zaposleniId) {
        KorisnikDTO kor;
        try {
            kor = korisnikProxy.getKorisnikById(zaposleniId).getBody();
        }
        catch (Exception ex) {
            throw new KorisnikNotFoundException("Korisnik sa ID-jem " + zaposleniId + " ne postoji u sistemu korisnika!");
        }

        if (kor.getUloga().getId() != 1) {
            throw new SamoZaKorisnikeException("Samo zaposleni se mogu prihvatati termine!");
        }


        Termin termin = terminRepository.findById(terminId)
                .orElseThrow(() -> new TerminNotFoundException("Termin sa ID-em " + terminId + " ne postoji."));

        if (!"NA_CEKANJU".equals(termin.getStatus())) {
            throw new TerminVecPrihvacenException("Moguce je prihvatiti samo termine koji su na cekanju.");
        }

        termin.setZaposleniId(zaposleniId);
        termin.setStatus("POTVRDJEN");

        KorisnikDTO korisnik = korisnikProxy.getKorisnikById(termin.getKlijentId()).getBody();

        try {
            emailService.posaljiMejlPotvrde(korisnik.getEmail(),
                    termin.getDatum().toString(),
                    termin.getSat().toString());
        } catch (Exception e) {
            System.out.println("Mejl nije poslat, ali je termin sacuvan u bazi: " + e.getMessage());
        }

        return terminRepository.save(termin);
    }


}


