package io.github.akhabali.controller;

import io.github.akhabali.service.DealerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Dealer Not Found Advice: handle dealer not found exception
 */
@RestControllerAdvice
public class DealerNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(DealerNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String dealerNotFound(DealerNotFoundException ex) {
        return ex.getMessage();
    }
}