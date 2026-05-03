package com.rzk.edukacijaservice.service;

import com.rzk.edukacijaservice.dto.EdukacijaDTO;
import com.rzk.edukacijaservice.dto.KorisnikDTO;
import com.rzk.edukacijaservice.dto.NovostDTO;
import com.rzk.edukacijaservice.exception.*;
import com.rzk.edukacijaservice.model.Edukacija;
import com.rzk.edukacijaservice.model.Novost;
import com.rzk.edukacijaservice.model.Prijava;
import com.rzk.edukacijaservice.proxy.KorisnikProxy;
import com.rzk.edukacijaservice.repository.EdukacijaRepository;
import com.rzk.edukacijaservice.repository.NovostRepository;
import com.rzk.edukacijaservice.repository.PrijavaRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class EdukacijaService {

    private final EdukacijaRepository edukacijaRepository;
    private final NovostRepository novostRepository;
    private final PrijavaRepository prijavaRepository;

    private final KorisnikProxy korisnikProxy;

    public List<Edukacija> vratiSveEdukacije () {
        return edukacijaRepository.findAll();
    }

    public List<Edukacija> vratiDostupneEdukacije() {

        List<Edukacija> buduceEdukacije = edukacijaRepository.findByDatumAfter(LocalDate.now());

        return buduceEdukacije.stream().filter(e -> {
            int brojPrijava = prijavaRepository.countByEdukacijaId(e.getId());
            return brojPrijava < e.getMaxPolaznika();
        }).toList();
    }


    // KORISNIK

    public Prijava prijavaNaEdukaciju (int klijentId, int edukacijaId) {
        KorisnikDTO kor;
        try {
            kor = korisnikProxy.getKorisnikById(klijentId).getBody();
        }
        catch (Exception ex) {
            throw new KorisnikNotFoundException("Korisnik sa ID-jem " + klijentId + " ne postoji u sistemu korisnika!");
        }

        if (kor.getUloga().getId() != 2) {
            throw new SamoZaKorisnikeException("Samo klijenti se mogu prijaviti na edukaciju!");
        }

        Edukacija e = edukacijaRepository.findById(edukacijaId)
                .orElseThrow(() -> new EdukacijaNotFoundException("Edukacija sa id-jem: " + edukacijaId + " ne postoji!"));

        if (e.getDatum().isBefore(LocalDate.now())) {
            throw new EdukacijaIsteklaException("Ne mozete se prijaviti na edukaciju koja je vec prosla.");
        }

        if (prijavaRepository.existsByEdukacijaIdAndKlijentId(edukacijaId, klijentId)) {
            throw new EdukacijaNemaMestaException("Klijent je vecd prijavljen na ovu edukaciju!");
        }

        int brojPrijava = prijavaRepository.countByEdukacijaId(edukacijaId);
        if (brojPrijava >= e.getMaxPolaznika()) {
            throw new EdukacijaNemaMestaException("Nema vise slobodnih mesta!");
        }

        Prijava p = new Prijava();
        p.setEdukacija(e);
        p.setKlijentId(klijentId);
        p.setDatumPrijave(LocalDate.now());

        return prijavaRepository.save(p);
    }

    @Transactional
    public boolean odjaviKlijenta(int edukacijaId, int klijentId) {
        KorisnikDTO kor;
        try {
            kor = korisnikProxy.getKorisnikById(klijentId).getBody();
        }
        catch (Exception ex) {
            throw new KorisnikNotFoundException("Korisnik sa ID-jem " + klijentId + " ne postoji u sistemu korisnika!");
        }

        if (kor.getUloga().getId() != 2) {
            throw new SamoZaKorisnikeException("Samo klijenti se mogu odjaiti sa edukaciju!");
        }

        if (prijavaRepository.existsByEdukacijaIdAndKlijentId(edukacijaId, klijentId)) {
            prijavaRepository.deleteByEdukacijaIdAndKlijentId(edukacijaId, klijentId);
            return true;
        }
        return false;
    }

    public List<Edukacija> vratiMojeEdukacije(int klijentId) {
        KorisnikDTO kor;
        try {
            kor = korisnikProxy.getKorisnikById(klijentId).getBody();
        }
        catch (Exception ex) {
            throw new KorisnikNotFoundException("Korisnik sa ID-jem " + klijentId + " ne postoji u sistemu korisnika!");
        }

        List<Prijava> prijave = prijavaRepository.findByKlijentId(klijentId);

        List<Edukacija> edukacijaIds = prijave.stream()
                .map(Prijava::getEdukacija)
                .toList();

        return edukacijaIds;
    }



    // ZAPOSLENI

    public Edukacija dodajNovuEdukaciju(EdukacijaDTO dto) {
        KorisnikDTO kor;
        try {
            kor = korisnikProxy.getKorisnikById(dto.getPredavacId()).getBody();
        }
        catch (Exception ex) {
            throw new KorisnikNotFoundException("Korisnik sa ID-jem " + dto.getPredavacId() + " ne postoji u sistemu korisnika!");
        }

        if (kor.getUloga().getId() != 1) {
            throw new SamoZaKorisnikeException("Samo zaposleni mogu praviti edukacije!");
        }

        Edukacija e = new Edukacija();

        e.setNaziv(dto.getNaziv());
        e.setOpis(dto.getOpis());
        e.setCena(dto.getCena());
        e.setMaxPolaznika(dto.getMaxPolaznika());
        e.setPredavacId(dto.getPredavacId());
        e.setDatum(dto.getDatum());
        e.setSat(dto.getSat());

        return edukacijaRepository.save(e);
    }

    @Transactional
    public boolean obrisiEdukaciju(int id) {
        Edukacija ed = edukacijaRepository.findById(id).orElseThrow(
            () -> new EdukacijaNotFoundException("Edukacija sa ID-jem " + id + " ne postoji."));

        prijavaRepository.deleteByEdukacijaId(id);
        edukacijaRepository.deleteById(id);

        return true;
    }

    public Edukacija izmeniDatumEdukacije(int id, LocalDate noviDatum) {
        Edukacija e = edukacijaRepository.findById(id).orElse(null);

        if (e != null && noviDatum.isAfter(LocalDate.now())) {
            e.setDatum(noviDatum);
            return edukacijaRepository.save(e);
        }
        return null;
    }

    public List<KorisnikDTO> getPrijavljeneNaEdukaciju (int edukacijaId) {
        if (!edukacijaRepository.existsById(edukacijaId)) {
            throw new EdukacijaNotFoundException("Edukacija sa ID " + edukacijaId + " ne postoji.");
        }

        List<Prijava> prijave = prijavaRepository.findByEdukacijaId(edukacijaId);

        return prijave.stream().map(p -> {
            var klijent = korisnikProxy.getKorisnikById(p.getKlijentId());

            return KorisnikDTO.builder()
                    .id(klijent.getBody().getId())
                    .ime(klijent.getBody().getIme())
                    .prezime(klijent.getBody().getPrezime())
                    .email(klijent.getBody().getEmail())
                    .telefon(klijent.getBody().getTelefon())
                    .uloga(klijent.getBody().getUloga())
                    .build();

        }).toList();
    }








    // NOVOSTI

    public List<Novost> vratiSveNovosti () {
        return novostRepository.findAllByOrderByDatumObjaveDesc();
    }

    public Novost sacuvajNovost(NovostDTO dto) {
        Novost n = new Novost();
        n.setNaslov(dto.getNaslov());
        n.setSadrzaj(dto.getSadrzaj());
        n.setDatumObjave(LocalDate.now());

        return novostRepository.save(n);
    }

}
