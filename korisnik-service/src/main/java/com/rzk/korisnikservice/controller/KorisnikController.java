package com.rzk.korisnikservice.controller;

import com.rzk.korisnikservice.dto.KorisnikDTO;
import com.rzk.korisnikservice.dto.PrijavaDTO;
import com.rzk.korisnikservice.model.Korisnik;
import com.rzk.korisnikservice.service.KorisnikService;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/api/korisnik")
public class KorisnikController {

    private final KorisnikService korisnikService;

//    {
//        "ime": "Jelena",
//            "prezime": "Jelić",
//            "email": "jelena.j@example.com",
//            "lozinka": "Projekat2024",
//            "brojTelefona": "+381639876543"
//    }

    @PostMapping("/registracija")
    @RateLimiter(name = "registracijaLimiter", fallbackMethod = "registracijaFallback")
    public ResponseEntity<?> registruj (@Valid @RequestBody KorisnikDTO dto) {
        log.info("Pokusaj registracije...");

        Korisnik sacuvaniKorisnik = korisnikService.registrujKorisnika(dto);

        if (sacuvaniKorisnik != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(sacuvaniKorisnik);
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Greska: Email je zauzet.");
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<Korisnik> getKorisnikById(@PathVariable("id") @Min(1) int id) {
        Korisnik korisnik = korisnikService.getKorisnikById(id);
        return korisnik != null ? ResponseEntity.ok(korisnik) : ResponseEntity.notFound().build();
    }

    @PostMapping("/prijava")
    public ResponseEntity<?> prijaviSe(@Valid @RequestBody PrijavaDTO prijavaDTO) {
        Korisnik korisnik = korisnikService.prijavaKorisnika(prijavaDTO);
        return new ResponseEntity<>("Uspesna prijava " + prijavaDTO.getEmail(), HttpStatus.ACCEPTED);
    }


    // FALLBACK METODA

    public ResponseEntity<?> registracijaFallback (RequestNotPermitted e) {
        log.error("LIMITER JE NAPOKON PRORADIO I BLOKIRAO TE!");
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                .body("Previse zahteva. Molimo pokusajte kasnije.");
    }
}
