package com.rzk.terminservice.controller;

import com.rzk.terminservice.dto.KorisnikDTO;
import com.rzk.terminservice.dto.TerminDTO;
import com.rzk.terminservice.exception.KorisnikNotFoundException;
import com.rzk.terminservice.exception.SamoZaKorisnikeException;
import com.rzk.terminservice.model.StavkaTermina;
import com.rzk.terminservice.model.Termin;
import com.rzk.terminservice.model.Usluga;
import com.rzk.terminservice.proxy.KorisnikProxy;
import com.rzk.terminservice.service.TerminService;
import feign.Request;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/api/termin")
public class TerminController {

   private final TerminService terminService;

   private final KorisnikProxy korisnikProxy;

   @GetMapping("/mojiTermini/{klijentId}")
   public ResponseEntity<List<Termin>> getBuduciTermini(@PathVariable @Min(1) Integer klijentId) {
      List<Termin> termini = terminService.getBuduciTerminiKlijenta(klijentId);

      if (termini.isEmpty()) {
         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(termini, HttpStatus.OK);
   }

//   {
//      "datum": "2026-05-20",
//           "sat": 10,
//           "uslugaIds": [1, 2, 3]
//   }

   @PostMapping("/zakazi/{klijentId}")
   @RateLimiter(name = "zakazivanjeLimiter", fallbackMethod = "fallbackZakazi")
   public ResponseEntity<Termin> zakazi(
           @PathVariable @Min(1) int klijentId,
           @Valid @RequestBody TerminDTO dto) {

      Termin noviTermin = terminService.zakaziNoviTermin(dto, klijentId);
      return new ResponseEntity<>(noviTermin, HttpStatus.CREATED);
   }

   @PutMapping("/otkaziTermin/{terminId}/{klijentId}")
   public ResponseEntity<Termin> otkaziStatus(@PathVariable @Min(1) Integer terminId,
                                              @PathVariable @Min(1) Integer klijentId) {
      Termin otkazanTermin = terminService.otkaziSvojTermin(terminId, klijentId);
      return ResponseEntity.ok(otkazanTermin);
   }



   // ZAPOSLENI

   @GetMapping("/getPotvrdjeni")
   public List<TerminDTO> getPotvrdjeniTermini() {
      return terminService.getPotvrdjeniTermini();
   }

   @GetMapping("/getTerminDTOById/{id}")
   public ResponseEntity<TerminDTO> getTerminDTOById(@PathVariable Integer id) {
      TerminDTO dto = terminService.getTerminDTO(id);
      return ResponseEntity.ok(dto);
   }

   @GetMapping("/getTerminById/{id}")
   public ResponseEntity<Termin> getById(@PathVariable Integer id) {
      Termin termin = terminService.getTermin(id);
      return ResponseEntity.ok(termin);
   }

   @GetMapping("/slobodni")
   @CircuitBreaker(name = "terminiSlobodni", fallbackMethod = "fallbackSlobodni")
   public ResponseEntity<List<Termin>> getSlobodni () {
      List<Termin> termini = terminService.getSlobodniTermini();

      if (termini.isEmpty()) {
         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(termini, HttpStatus.OK);
   }

   @PutMapping("/prihvati/{terminId}/{zaposleniId}")
   public ResponseEntity<?> prihvati(@PathVariable @Min(1) Integer terminId,
                                     @PathVariable @Min(1) Integer zaposleniId) {
      Termin potvrdjenTermin = terminService.prihvatiTermin(terminId, zaposleniId);
      if (potvrdjenTermin != null) {
         return new ResponseEntity<>(potvrdjenTermin, HttpStatus.OK);
      }
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
   }


   // FALLBACK METODA
   public ResponseEntity<?> fallbackZakazi (RequestNotPermitted e) {
      return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
              .body("Previse pokusaja zakazivanja. Molimo sasekajte.");
   }

   public ResponseEntity<?> fallbackSlobodni(RequestNotPermitted e) {
      log.error("Circuit Breaker otvoren za slobodne termine!");
      return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
   }

}
