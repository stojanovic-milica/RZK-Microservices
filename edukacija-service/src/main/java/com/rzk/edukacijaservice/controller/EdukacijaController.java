package com.rzk.edukacijaservice.controller;

import com.rzk.edukacijaservice.dto.EdukacijaDTO;
import com.rzk.edukacijaservice.dto.KorisnikDTO;
import com.rzk.edukacijaservice.dto.NovostDTO;
import com.rzk.edukacijaservice.model.Edukacija;
import com.rzk.edukacijaservice.model.Novost;
import com.rzk.edukacijaservice.model.Prijava;
import com.rzk.edukacijaservice.service.EdukacijaService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.pqc.crypto.ExchangePair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/api/edukacija")
public class EdukacijaController {

    private final EdukacijaService edukacijaService;

    @GetMapping("/sveEdukacije")
    @CircuitBreaker(name = "edukacijaService", fallbackMethod = "fallbackVratiSveEdukacije")
    public ResponseEntity<List<Edukacija>> vratiSveEdukacije() {
        List<Edukacija> edukacije = edukacijaService.vratiSveEdukacije();
        return new ResponseEntity<>(edukacije, HttpStatus.OK);
    }

    @GetMapping("/sveDostupneEdukacije")
    public ResponseEntity<List<Edukacija>> getDostupne() {
        return new ResponseEntity<>(edukacijaService.vratiDostupneEdukacije(), HttpStatus.OK);
    }


    // KORISNIK

    @PostMapping("/prijavaEdukacija")
    @RateLimiter(name = "prijavaLimiter", fallbackMethod = "fallbackPrijava")
    public ResponseEntity<Prijava> prijavaNaEdukaciju(@RequestParam @Min(1) int klijentId, @Min(1) @RequestParam int edukacijaId) {
        Prijava nova = edukacijaService.prijavaNaEdukaciju(klijentId, edukacijaId);
        if (nova != null) {
            return new ResponseEntity<>(nova, HttpStatus.CREATED);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/otkazi/{edukacijaId}/{klijentId}")
    public ResponseEntity<String> otkaziPrijavu(@PathVariable @Min(1) int edukacijaId, @PathVariable @Min(1) int klijentId) {
        boolean uspesno = edukacijaService.odjaviKlijenta(edukacijaId, klijentId);

        if (uspesno) {
            return ResponseEntity.ok("Uspesno ste se odjavili sa edukacije.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Greska: Prijava ne postoji, pa se ne moze ni obrisati.");
        }
    }

    @GetMapping("/mojePrijave/{klijentId}")
    public ResponseEntity<List<Edukacija>> getMojeEdukacije(@PathVariable @Min(1) int klijentId) {
        List<Edukacija> edukacije = edukacijaService.vratiMojeEdukacije(klijentId);
        return ResponseEntity.ok(edukacije);
    }


    // ZAPOSLENI

//    {
//        "naziv": "Edukacija za nokte - Basic",
//            "opis": "Osnovni kurs manikira i ojačavanja noktiju prirodnim gelom.",
//            "cena": 15000.00,
//            "maxPolaznika": 10,
//            "predavacId": 11,
//            "datum": "2026-05-20",
//            "sat": 12
//    }

    @PostMapping("/dodajEdukaciju")
    public ResponseEntity<?> dodaj(@RequestBody @Valid EdukacijaDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            List<String> greske = result.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .toList();
            return new ResponseEntity<>(greske, HttpStatus.BAD_REQUEST);
        }

        Edukacija nova = edukacijaService.dodajNovuEdukaciju(dto);
        return new ResponseEntity<>(nova, HttpStatus.CREATED);
    }

    @DeleteMapping("/obrisi/{id}")
    public ResponseEntity<String> obrisi(@PathVariable @Min(1) int id) {
        boolean obrisano = edukacijaService.obrisiEdukaciju(id);

        if (obrisano) {
            return ResponseEntity.ok("Uspesno obrisana edukacija sa ID: " + id + " i sve njene prijave.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Greska: Edukacija sa ID: " + id + " ne postoji u bazi.");
        }
    }

    @PatchMapping("/izmeniEdukaciju/{id}")
    public ResponseEntity<?> izmeniDatumEdukacije(
            @PathVariable @Min(1) int id,
            @RequestParam @Future(message = "Datum mora biti u buducnosti") LocalDate noviDatum) {

        Edukacija izmenjena = edukacijaService.izmeniDatumEdukacije(id, noviDatum);

        if (izmenjena == null) {
            return new ResponseEntity<>("Edukacija nije pronadjena.", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(izmenjena, HttpStatus.OK);
    }

    @GetMapping("/{edukacijaId}/polaznici")
    public ResponseEntity<List<KorisnikDTO>> getPrijavljeneNaEdukaciju(@PathVariable @Min(1) int edukacijaId) {
        List<KorisnikDTO> polaznici = edukacijaService.getPrijavljeneNaEdukaciju(edukacijaId);
        return ResponseEntity.ok(polaznici);
    }


    // NOVOSTI

    @GetMapping("/sveNovosti")
    public List<Novost> vratiSveNovosti() {
        return edukacijaService.vratiSveNovosti();
    }

//    {
//        "naslov": "Nova edukacija u salonu!",
//            "sadrzaj": "Sa velikim zadovoljstvom vas obaveštavamo da otvaramo prijave za napredni kurs manikira koji počinje sledećeg meseca. Broj mesta je ograničen!"
//    }

    @PostMapping("/dodajNovost")
    @Retry(name = "novostRetry", fallbackMethod = "fallbackDodajNovost")
    public ResponseEntity<?> dodajNovost(@Valid @RequestBody NovostDTO dto, BindingResult result) {

        if (result.hasErrors()) {
            return new ResponseEntity<>(result.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage).toList(), HttpStatus.BAD_REQUEST);
        }

        Novost nova = edukacijaService.sacuvajNovost(dto);
        return new ResponseEntity<>(nova, HttpStatus.CREATED);
    }


    // FALLBACK METODE

    public ResponseEntity<List<?>> fallbackVratiSveEdukacije (RequestNotPermitted e) {
        return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
    }

    public ResponseEntity<?> fallbackPrijava (RequestNotPermitted ex) {
        log.error("LIMITER JE NAPOKON PRORADIO I BLOKIRAO TE!");
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                .body("Previse zahteva. Molimo pokusajte kasnije.");
    }

    public ResponseEntity<?> fallbackDodajNovost (RequestNotPermitted e) {
        return new ResponseEntity<>("Sistem za novosti trenutno nije dostupan. Pokusajte kasnije.",
                HttpStatus.SERVICE_UNAVAILABLE);
    }

}
