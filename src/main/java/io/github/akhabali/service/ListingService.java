package io.github.akhabali.service;

import io.github.akhabali.errors.ListingAlreadyExistsException;
import io.github.akhabali.errors.ListingNotFoundException;
import io.github.akhabali.model.Listing;
import io.github.akhabali.model.ListingState;
import io.github.akhabali.repository.ListingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class ListingService {

    private final ListingRepository listingRepository;

    private final DealerService dealerService;

    public Listing findById(Long id) {
        return listingRepository.findById(id).orElseThrow(() -> new ListingNotFoundException(id));
    }

    public Listing createListing(Listing newListing) {
        if (newListing.getId() != null) {
            throw new ListingAlreadyExistsException(newListing.getId());
        }

        //RULE: all new created listing should be in state draft
        newListing.setState(ListingState.draft);
        newListing.setCreatedAt(System.currentTimeMillis());

        return listingRepository.save(newListing);
    }

    public Listing updateListing(Listing listing) {

        //RULE: all new created/updated listing should be in state draft
        listing.setState(ListingState.draft);

        return listingRepository.save(listing);
    }
}
