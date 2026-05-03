package com.rzk.edukacijaservice.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Total errors: ").append(ex.getErrorCount()).append(", ");
        for (FieldError e : ex.getFieldErrors()) {
            stringBuilder.append("Field: ").append(e.getField()).append(" - ").append(e.getDefaultMessage()).append(", ");
        }

        ErrorEntity errorEntity = new ErrorEntity(stringBuilder.toString(), LocalDateTime.now());
        return new ResponseEntity<>(errorEntity, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(jakarta.validation.ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolation(jakarta.validation.ConstraintViolationException ex) {
        StringBuilder stringBuilder = new StringBuilder();

        var violations = ex.getConstraintViolations();
        stringBuilder.append("Total errors: ").append(violations.size()).append(", ");

        for (var violation : violations) {
            String fullPath = violation.getPropertyPath().toString();
            String fieldName = fullPath.contains(".")
                    ? fullPath.substring(fullPath.lastIndexOf(".") + 1)
                    : fullPath;

            stringBuilder.append("Field: ").append(fieldName)
                    .append(" - ").append(violation.getMessage()).append(", ");
        }

        ErrorEntity errorEntity = new ErrorEntity(stringBuilder.toString(), LocalDateTime.now());
        return new ResponseEntity<>(errorEntity, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EdukacijaNotFoundException.class)
    public final ResponseEntity<ErrorEntity> handleEdukacijaNotFoundException (EdukacijaNotFoundException e) {
        ErrorEntity errorEntity = new ErrorEntity(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorEntity, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EdukacijaNemaMestaException.class)
    public final ResponseEntity<ErrorEntity> handleNemaMestaException(EdukacijaNemaMestaException e) {
        ErrorEntity errorEntity = new ErrorEntity(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorEntity, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EdukacijaIsteklaException.class)
    public final ResponseEntity<ErrorEntity> handleIsteklaException(EdukacijaIsteklaException e) {
        ErrorEntity errorEntity = new ErrorEntity(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorEntity, HttpStatus.BAD_REQUEST);
    }

    // KORISNIK EXCPETION
    @ExceptionHandler(KorisnikNotFoundException.class)
    public final ResponseEntity<ErrorEntity> handleKorisnikException(KorisnikNotFoundException e) {
        ErrorEntity errorEntity = new ErrorEntity(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorEntity, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SamoZaKorisnikeException.class)
    public final ResponseEntity<ErrorEntity> handleKorisnikException(SamoZaKorisnikeException e) {
        ErrorEntity errorEntity = new ErrorEntity(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorEntity, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorEntity> handleException(Exception e) {
        ErrorEntity errorEntity = new ErrorEntity(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(errorEntity, HttpStatus.BAD_REQUEST);
    }

}
