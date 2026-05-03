package com.rzk.terminservice.service;

import com.rzk.terminservice.dto.UslugaDTO;
import com.rzk.terminservice.exception.UslugaNotFoundException;
import com.rzk.terminservice.model.Recenzija;
import com.rzk.terminservice.model.StavkaTermina;
import com.rzk.terminservice.model.Termin;
import com.rzk.terminservice.model.Usluga;
import com.rzk.terminservice.repository.RecenzijaRepository;
import com.rzk.terminservice.repository.StavkaTerminaRepository;
import com.rzk.terminservice.repository.TerminRepository;
import com.rzk.terminservice.repository.UslugaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UslugaService {

    private final UslugaRepository uslugaRepository;
    private final TerminRepository terminRepository;
    private final RecenzijaRepository recenzijaRepository;
    private final StavkaTerminaRepository stavkaTerminaRepository;

    public Usluga getUslugaById (Integer uslugaId) {
        return uslugaRepository.findById(uslugaId).orElse(null);
    }

    public UslugaDTO getUslugaDTO (Integer id) {
        Usluga usluga = uslugaRepository.findById(id)
                .orElseThrow(() -> new UslugaNotFoundException("Usluga sa ID " + id + " ne postoji!"));

        return new UslugaDTO(usluga.getNaziv(), usluga.getOpis(), usluga.getCena());
    }

    public List<Usluga> vratiSveUsluge() {
        return uslugaRepository.findAll();
    }

    public Usluga dodajNovuUslugu(UslugaDTO dto) {
        Usluga novaUsluga = new Usluga();

        novaUsluga.setNaziv(dto.getNaziv());
        novaUsluga.setOpis(dto.getOpis());
        novaUsluga.setCena(dto.getCena());

        return uslugaRepository.save(novaUsluga);
    }

    public Usluga obrisiUslugu(int id) {
        Usluga usluga = uslugaRepository.findById(id).orElseThrow(
                () -> new UslugaNotFoundException("Usluga sa ID-jem " + id + " nije pronadjena.")
        );

        List<StavkaTermina> stavke = stavkaTerminaRepository.findByUslugaId(id);

        for (StavkaTermina stavkaTermina : stavke) {
            stavkaTerminaRepository.delete(stavkaTermina);
        }

        List<Recenzija> recenzije = recenzijaRepository.findByUslugaId(id);
        for (Recenzija r : recenzije) {
            recenzijaRepository.delete(r);
        }

        uslugaRepository.delete(usluga);

        return usluga;
    }

}
