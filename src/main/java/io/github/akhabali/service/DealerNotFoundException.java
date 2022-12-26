package io.github.akhabali.service;

/**
 * Dealer Not Found: use me when a Dealer id was not found
 */
public class DealerNotFoundException extends RuntimeException {

    DealerNotFoundException(Long id) {
        super("{\"error\": \"No dealer was found with id=" + id + "\"}");
    }
}
