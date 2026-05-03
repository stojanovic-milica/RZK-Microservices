package com.rzk.terminservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class UslugaNotFoundException extends RuntimeException {

    private String message;

}
