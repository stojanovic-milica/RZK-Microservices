package com.rzk.terminservice.controller;

import com.rzk.terminservice.dto.UslugaDTO;
import com.rzk.terminservice.model.Usluga;
import com.rzk.terminservice.service.UslugaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/usluga")
@AllArgsConstructor
public class UslugaController {

    private UslugaService uslugaService;

    @GetMapping("/getById/{id}")
    public ResponseEntity<UslugaDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(uslugaService.getUslugaDTO(id));
    }

    @GetMapping("/sveUsluge")
    public ResponseEntity<List<Usluga>> getCenovnik() {
        return ResponseEntity.ok(uslugaService.vratiSveUsluge());
    }

//    {
//        "naziv": "Šišanje i feniranje",
//            "opis": "Kompletna usluga šišanja sa pranjem kose i profesionalnim feniranjem.",
//            "cena": 2500.00,
//            "trajanjeMinuta": 45,
//            "kategorija": "Ženske frizure"
//    }

    @PostMapping("/dodajUslugu")
    public ResponseEntity<?> dodajNovuUslugu (@Valid @RequestBody UslugaDTO dto, BindingResult result) {
        if (result.hasErrors()) {
            StringBuilder sveGreske = new StringBuilder();

            for (ObjectError error : result.getAllErrors()) {
                sveGreske.append(error.getDefaultMessage()).append("\n");
            }

            return new ResponseEntity<>(sveGreske.toString(), HttpStatus.BAD_REQUEST);
        }

        Usluga usluga = uslugaService.dodajNovuUslugu(dto);

        if (usluga == null) {
            return new ResponseEntity<>("Doslo je do greske prilikom cuvanja usluge. Proverite bazu podataka.",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(usluga, HttpStatus.CREATED);
    }

    @DeleteMapping("/obrisiUslugu/{idUsluge}")
    public ResponseEntity<?> obrisiUslugu (@PathVariable @Min(1) int idUsluge) {
        Usluga obrisano = uslugaService.obrisiUslugu(idUsluge);

        if (obrisano != null) {
            return new ResponseEntity<>("Usluga uspesno obrisana.", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

    }

}