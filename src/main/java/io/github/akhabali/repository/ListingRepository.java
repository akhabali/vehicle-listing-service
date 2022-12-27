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
}
