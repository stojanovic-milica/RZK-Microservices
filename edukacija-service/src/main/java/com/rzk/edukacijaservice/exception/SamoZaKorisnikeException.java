package com.rzk.edukacijaservice.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SamoZaKorisnikeException extends RuntimeException {

    private String message;

}
