package com.rzk.edukacijaservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EdukacijaNemaMestaException extends RuntimeException {

    private String message;

}
