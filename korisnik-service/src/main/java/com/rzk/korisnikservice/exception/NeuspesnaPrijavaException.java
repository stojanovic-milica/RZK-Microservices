package com.rzk.korisnikservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NeuspesnaPrijavaException extends RuntimeException {

    private String message;

}
