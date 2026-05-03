package com.rzk.terminservice.service;

import com.rzk.terminservice.dto.KorisnikDTO;
import com.rzk.terminservice.dto.UslugaDTO;
import com.rzk.terminservice.dto.UslugaStatistikaDTO;
import com.rzk.terminservice.exception.KorisnikNotFoundException;
import com.rzk.terminservice.exception.SamoZaKorisnikeException;
import com.rzk.terminservice.exception.UslugaNotFoundException;
import com.rzk.terminservice.model.Recenzija;
import com.rzk.terminservice.model.Usluga;
import com.rzk.terminservice.proxy.KorisnikProxy;
import com.rzk.terminservice.repository.RecenzijaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecenzijaService {

    private final RecenzijaRepository recenzijaRepository;
    private final UslugaService uslugaService;

    private final KorisnikProxy korisnikProxy;

    public Recenzija dodajRecenziju (Recenzija recenzija) {
        KorisnikDTO kor;
        try {
            kor = korisnikProxy.getKorisnikById(recenzija.getKlijentId()).getBody();
        }
        catch (Exception ex) {
            throw new KorisnikNotFoundException("Korisnik sa ID-jem " + recenzija.getKlijentId() + " ne postoji u sistemu korisnika!");
        }

        if (kor.getUloga().getId() != 2) {
            throw new SamoZaKorisnikeException("Samo klijenti se mogu ostavljati recenzije!");
        }

        Usluga info = uslugaService.getUslugaById(recenzija.getUslugaId());

        if (info == null) {
            throw new UslugaNotFoundException("Greska: Usluga sa ID " + recenzija.getUslugaId() + " ne postoji u sistemu!");
        }

        return recenzijaRepository.save(recenzija);
    }

    public List<UslugaStatistikaDTO> getKompletnaStatistika () {
        List<UslugaStatistikaDTO> stats = recenzijaRepository.getSirovaStatistika();

        for (UslugaStatistikaDTO s : stats) {
            Usluga info = uslugaService.getUslugaById(s.getUslugaId());
            if (info != null) {
                s.setNaziv(info.getNaziv());
            } else {
                s.setNaziv("Nepoznata usluga (ID: " + s.getUslugaId() + ")");
            }
        }

        return stats;
    }

}
