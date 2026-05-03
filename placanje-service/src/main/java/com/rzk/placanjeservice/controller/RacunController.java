package com.rzk.placanjeservice.controller;

import com.rzk.placanjeservice.dto.RacunDTO;
import com.rzk.placanjeservice.dto.StatistikaRadnikaDTO;
import com.rzk.placanjeservice.dto.TerminDTO;
import com.rzk.placanjeservice.model.Racun;
import com.rzk.placanjeservice.service.RacunService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/racun")
@AllArgsConstructor
public class RacunController {

    private final RacunService racunService;

    @GetMapping("/zaNaplatu")
    public ResponseEntity<List<TerminDTO>> getZaNaplatu() {
        return ResponseEntity.ok(racunService.getTerminiKojiCekajuNaplatu());
    }

    @PostMapping("/generisi/{idTermina}")
//    @CircuitBreaker(name = "racunService", fallbackMethod = "fallbackPlacanje")
    public ResponseEntity<Racun> generisiRacun(@PathVariable @Min(1) Integer idTermina) {
        return new ResponseEntity<>(racunService.kreirajRacun(idTermina), HttpStatus.CREATED);
    }

    @GetMapping("/statistikaRadnika")
    public ResponseEntity<List<StatistikaRadnikaDTO>> getStatistika() {
        return ResponseEntity.ok(racunService.getStatistikaPoRadnicima());
    }

    public ResponseEntity<?> fallbackPlacanje() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Sistem za izdavanje racuna je trenutno preopterecen. Molimo pokusajte za minut.");
    }
}