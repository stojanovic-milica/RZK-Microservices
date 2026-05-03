package com.rzk.placanjeservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RacunVecGenerisanException extends RuntimeException {

    private String message;

}
