package io.github.akhabali.errors;

/**
 * Dealer Not Found: use me when a Dealer id was not found
 */
public class ListingNotFoundException extends RuntimeException {

    public ListingNotFoundException(Long id) {
        super("No dealer's listing was found with id=" + id);
    }
}
