package io.github.akhabali.repository;

import io.github.akhabali.model.Listing;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Listing Repository: responsible for gathering dealer's listing data from the database
 */
public interface ListingRepository extends JpaRepository<Listing, Long> {
}
