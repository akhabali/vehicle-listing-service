package io.github.akhabali.controller;

import io.github.akhabali.dto.ErrorDto;
import io.github.akhabali.errors.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
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
    @ExceptionHandler(value = {DealerNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto dealerNotFound(DealerNotFoundException ex) {
        log.debug("Error: ", ex);
        return new ErrorDto(ex.getMessage());

    }

    @ResponseBody
    @ExceptionHandler({DealerAlreadyExistsException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorDto dealerAlreadyExists(DealerAlreadyExistsException ex) {
        log.debug("Error: ", ex);
        return new ErrorDto(ex.getMessage());
    }


    @ResponseBody
    @ExceptionHandler(value = {ListingNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDto listingNotFound(ListingNotFoundException ex) {
        log.debug("Error: ", ex);
        return new ErrorDto(ex.getMessage());

    }

    @ResponseBody
    @ExceptionHandler({ListingAlreadyExistsException.class})
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorDto listingAlreadyExists(ListingAlreadyExistsException ex) {
        log.debug("Error: ", ex);
        return new ErrorDto(ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler({ListingTierLimitExceeded.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorDto listingLimitExceeded(ListingTierLimitExceeded ex) {
        log.debug("Error: ", ex);
        return new ErrorDto(ex.getMessage());
    }
}