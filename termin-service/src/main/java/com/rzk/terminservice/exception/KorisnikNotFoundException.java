package com.rzk.terminservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class KorisnikNotFoundException extends RuntimeException {

    private String message;

}
