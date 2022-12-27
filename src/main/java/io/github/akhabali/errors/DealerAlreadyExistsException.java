package io.github.akhabali.errors;

/**
 * Dealer Not Found: use me when a Dealer id was not found
 */
public class DealerAlreadyExistsException extends RuntimeException {

    public DealerAlreadyExistsException(Long id) {
        super("Dealer already exists with id=" + id);
    }
}
