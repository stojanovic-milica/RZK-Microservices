package com.rzk.terminservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TerminVecPrihvacenException extends RuntimeException {

    private String message;

}
