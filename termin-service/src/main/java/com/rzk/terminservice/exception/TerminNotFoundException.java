package com.rzk.terminservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TerminNotFoundException extends RuntimeException {

    private String message;

}
