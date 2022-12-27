package io.github.akhabali.errors;

/**
 * Dealer Not Found: use me when a Dealer id was not found
 */
public class DealerNotFoundException extends RuntimeException {

    public DealerNotFoundException(Long id) {
        super("No dealer was found with id=" + id);
    }
}
