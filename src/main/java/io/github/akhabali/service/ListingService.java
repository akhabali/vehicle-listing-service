package io.github.akhabali.service;

import io.github.akhabali.errors.ListingAlreadyExistsException;
import io.github.akhabali.errors.ListingNotFoundException;
import io.github.akhabali.errors.ListingTierLimitExceeded;
import io.github.akhabali.model.Dealer;
import io.github.akhabali.model.Listing;
import io.github.akhabali.model.ListingState;
import io.github.akhabali.repository.ListingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Listing> findListingByDealerId(Long dealerId, ListingState state) {

        return listingRepository.findListingByDealerIdAndState(dealerId, state);

    }

    public Listing publishListing(Long listingId, boolean forcePublish) {
        Listing listing = listingRepository.findById(listingId).orElseThrow(() -> new ListingNotFoundException(listingId));
        // check listing state
        if (ListingState.published.equals(listing.getState())) {
            return listing; // nothing to do
        }

        // check tier limit
        Dealer dealer = listing.getDealer();
        Long publishedListingCount = listingRepository.countListingByDealerAndState(dealer.getId(), ListingState.published);
        Long tierLimit = dealer.getTierLimit();

        if (publishedListingCount >= tierLimit) {
            if (!forcePublish) {
                throw new ListingTierLimitExceeded(dealer.getId());
            }

            // unpublish the oldest listing
            Listing oldestListing = listingRepository.findOldestListing(dealer.getId(), ListingState.published);
            oldestListing.setState(ListingState.draft);
            listingRepository.save(oldestListing);
        }

        // update listing state
        listing.setState(ListingState.published);

        //save to db
        return listingRepository.save(listing);
    }

    public void unpublishListing(Long listingId) {
        Listing listing = listingRepository.findById(listingId).orElseThrow(() -> new ListingNotFoundException(listingId));
        // check listing state
        if (ListingState.draft.equals(listing.getState())) {
            return; // nothing to do
        }

        listing.setState(ListingState.draft);
        listingRepository.save(listing);
    }
}
