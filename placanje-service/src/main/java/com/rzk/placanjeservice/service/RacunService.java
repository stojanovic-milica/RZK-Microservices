package com.rzk.placanjeservice.service;

import com.rzk.placanjeservice.dto.RacunDTO;
import com.rzk.placanjeservice.dto.StatistikaRadnikaDTO;
import com.rzk.placanjeservice.dto.TerminDTO;
import com.rzk.placanjeservice.dto.UslugaDTO;
import com.rzk.placanjeservice.exception.RacunVecGenerisanException;
import com.rzk.placanjeservice.exception.TerminNotFoundException;
import com.rzk.placanjeservice.model.Popust;
import com.rzk.placanjeservice.model.Racun;
import com.rzk.placanjeservice.model.StavkaRacuna;
import com.rzk.placanjeservice.proxy.TerminProxy;
import com.rzk.placanjeservice.repository.PopustRepository;
import com.rzk.placanjeservice.repository.RacunRepository;
import com.rzk.placanjeservice.repository.StavkaRacunaRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.bouncycastle.pqc.crypto.ExchangePair;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@AllArgsConstructor
public class RacunService {

    private final RacunRepository racunRepository;
    private final StavkaRacunaRepository stavkaRacunaRepository;
    private final PopustRepository popustRepository;

    private final TerminProxy terminProxy;

    @Transactional
    public Racun kreirajRacun(Integer id) {
        if (racunRepository.existsByTerminId(id)) {
            throw new RacunVecGenerisanException("Racun za termin sa ID-jem " + id + " je vec generisan!");
        }

        TerminDTO termin;

        try {
            termin = terminProxy.getTerminDTOById(id).getBody();
        }
        catch (Exception e) {
//            e.printStackTrace();
            throw new TerminNotFoundException("Termin sa ID-jem " + id + " nije pronadjen u sistemu termina.");
        }

        double ukupnaCenaPre = 0;
        Racun racun = new Racun();
        racun.setKlijentId(termin.getKlijentId());
        racun.setDatumIzdavanja(LocalDate.now());
        racun.setTerminId(id);

        Racun sacuvaniRacun = racunRepository.save(racun);

        for (Integer uslugaId : termin.getUslugaIds()) {
            UslugaDTO uslugaInfo = terminProxy.getUslugaById(uslugaId);

            StavkaRacuna sr = new StavkaRacuna();
            sr.setNazivUsluge(uslugaInfo.getNaziv());
            sr.setCenaUsluge(uslugaInfo.getCena());
            sr.setTerminId(id);
            sr.setRacun(sacuvaniRacun);

            ukupnaCenaPre += sr.getCenaUsluge();
            racun.getStavkaRacunas().add(sr);
            stavkaRacunaRepository.save(sr);
        }

        double finalnaCena = ukupnaCenaPre;

        Optional<Popust> dnevniPopust = popustRepository.findByDatumVazenja(termin.getDatum());

        if (dnevniPopust.isPresent()) {
            Popust p = dnevniPopust.get();
            double umanjenje = (ukupnaCenaPre * p.getProcenat()) / 100;
            finalnaCena = ukupnaCenaPre - umanjenje;

            racun.setPopust(p);
            racun.setPopustProcenat(p.getProcenat());
        }

        sacuvaniRacun.setUkupnaCenaPre(ukupnaCenaPre);
        sacuvaniRacun.setUkupnaCenaPosle(finalnaCena);

        return racunRepository.save(sacuvaniRacun);
    }

    public List<TerminDTO> getTerminiKojiCekajuNaplatu() {
        List<TerminDTO> sviPotvrdjeni = terminProxy.getPotvrdjeniTermini();

        List<Integer> placeniIds = racunRepository.findAllTerminIdsSaRacunom();

        return sviPotvrdjeni.stream()
                .filter(t -> !placeniIds.contains(t.getId()))
                .toList();
    }

    public List<StatistikaRadnikaDTO> getStatistikaPoRadnicima () {
        List<Racun> sviRacuni = racunRepository.findAll();

        double ukupnaSumaSve = sviRacuni.stream()
                .mapToDouble(Racun::getUkupnaCenaPosle)
                .sum();

//        if (ukupnaSumaSve == 0) return new ArrayList<>();

        Map<Integer, Double> zaradaPoRadniku = new HashMap<>();

        for (Racun r : sviRacuni) {
            Integer radnikId = terminProxy.getTerminDTOById(r.getTerminId()).getBody().getZaposleniId();


            if (radnikId != null) {
                zaradaPoRadniku.put(radnikId,
                        zaradaPoRadniku.getOrDefault(radnikId, 0.0) + r.getUkupnaCenaPosle());
            }
        }

        return zaradaPoRadniku.entrySet().stream()
                .map(entry -> {
                    double procenat = (entry.getValue() * 100) / ukupnaSumaSve;
                    return new StatistikaRadnikaDTO(entry.getKey(), entry.getValue(), procenat);
                })
                .toList();
    }


}