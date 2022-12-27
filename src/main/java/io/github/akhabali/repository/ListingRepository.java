package io.github.akhabali.repository;

import io.github.akhabali.model.Listing;
import io.github.akhabali.model.ListingState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Listing Repository: responsible for gathering dealer's listing data from the database
 */
public interface ListingRepository extends JpaRepository<Listing, Long> {


    @Query("SELECT l FROM Listing l WHERE l.dealer.id = ?1 AND l.state = ?2")
    List<Listing> findListingByDealerIdAndState(Long dealerId, ListingState state);


    @Query("SELECT COUNT(l) FROM Listing l WHERE l.dealer.id = ?1 AND l.state = ?2")
    Long countListingByDealerAndState(Long dealerId, ListingState state);

    @Query("SELECT l FROM Listing l WHERE l.dealer.id = ?1 AND l.state = ?2 ORDER BY l.createdAt desc LIMIT 1")
    Listing findOldestListing(Long id, ListingState state);
}
