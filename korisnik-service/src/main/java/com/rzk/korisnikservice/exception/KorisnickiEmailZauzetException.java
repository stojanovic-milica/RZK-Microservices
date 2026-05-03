package com.rzk.korisnikservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class KorisnickiEmailZauzetException extends RuntimeException {

    private String message;

}
