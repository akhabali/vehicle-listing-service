package io.github.akhabali.controller;

import io.github.akhabali.dto.ErrorDto;
import io.github.akhabali.errors.DealerAlreadyExistsException;
import io.github.akhabali.errors.DealerNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Dealer Not Found Advice: handle dealer not found exception
 */
@Log4j2
@RestControllerAdvice
public class GenericErrorAdvice {

    @ResponseBody
    @ExceptionHandler({DealerNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto notFound(DealerNotFoundException ex) {
        log.debug("Error: ", ex);
        return new ErrorDto(ex.getMessage());

    }

    @ResponseBody
    @ExceptionHandler(DealerAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorDto alreadyExists(DealerAlreadyExistsException ex) {
        log.debug("Error: ", ex);
        return new ErrorDto(ex.getMessage());
    }
}