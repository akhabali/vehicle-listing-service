package io.github.akhabali.errors;

/**
 * Dealer Not Found: use me when a Dealer id was not found
 */
public class ListingAlreadyExistsException extends RuntimeException {

    public ListingAlreadyExistsException(Long id) {
        super("Dealer's listing already exists with id=" + id);
    }
}
