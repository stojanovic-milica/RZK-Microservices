package com.rzk.terminservice.controller;

import com.rzk.terminservice.dto.UslugaStatistikaDTO;
import com.rzk.terminservice.model.Recenzija;
import com.rzk.terminservice.service.RecenzijaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recenzija")
public class RecenzijaController {

    private final RecenzijaService recenzijaService;

//    {
//        "ocena": 5,
//            "komentar": "Usluga je bila fantastična, osoblje je veoma ljubazno i profesionalno. Definitivno dolazim opet!",
//            "klijentId": 22,
//            "uslugaId": 3
//    }

    @PostMapping("/dodaj/{klijentId}")
    public ResponseEntity<Recenzija> dodaj(@Valid @RequestBody Recenzija recenzija,
                                           @PathVariable @Min(1) Integer klijentId) {
        recenzija.setKlijentId(klijentId);

        return ResponseEntity.ok(recenzijaService.dodajRecenziju(recenzija));
    }

    @GetMapping("/statistika")
    public ResponseEntity<List<UslugaStatistikaDTO>> getStatistikaUsluga() {
        List<UslugaStatistikaDTO> statistika = recenzijaService.getKompletnaStatistika();

        if (statistika.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(statistika);
    }

}
