package com.rzk.edukacijaservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ErrorEntity {

    private String message;
    private LocalDateTime localDateTime;
    
}
