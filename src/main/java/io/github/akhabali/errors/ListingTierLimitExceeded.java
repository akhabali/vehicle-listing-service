package io.github.akhabali.errors;

public class ListingTierLimitExceeded extends RuntimeException {

    public ListingTierLimitExceeded(Long id) {
        super("Tier limit exceeded for dealer id=" + id);
    }
}
