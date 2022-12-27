package io.github.akhabali.service;

import io.github.akhabali.errors.ListingAlreadyExistsException;
import io.github.akhabali.errors.ListingNotFoundException;
import io.github.akhabali.model.Listing;
import io.github.akhabali.repository.ListingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


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
        return listingRepository.save(newListing);
    }
}
