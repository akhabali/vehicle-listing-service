package io.github.akhabali.controller;

import io.github.akhabali.dto.ListingDto;
import io.github.akhabali.model.Dealer;
import io.github.akhabali.model.Listing;
import io.github.akhabali.model.ListingState;
import io.github.akhabali.service.DealerService;
import io.github.akhabali.service.ListingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Listing controller: The entry point to the operations authorized on a dealer listing
 */
@Log4j2
@RestController
@RequestMapping("/dealers/{dealer_id}")
@Tag(name = "Listing", description = "Dealer Car Listing Service")
@RequiredArgsConstructor
public class ListingController {

    private final ModelMapper modelMapper;


    private final DealerService dealerService;
    private final ListingService listingService;

    /**
     * Create a new vehicle listing for the dealer identified by 'dealer_id'
     * <p>
     * All the created listings should have state draft by default;
     *
     * @param dealerId   the id of the dealer
     * @param newListing vehicle information to be used to create the listing
     * @return the freshly created listing
     */
    @Operation(summary = "Create a new vehicle listing for the dealer identified by 'dealer_id'")
    @PostMapping(path = "/listing", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ListingDto newListing(@PathVariable("dealer_id") Long dealerId, @RequestBody ListingDto newListing) {
        return convertToDto(listingService.createListing(convertToEntity(newListing)));
    }

    private ListingDto convertToDto(Listing listing) {
        return modelMapper.map(listing, ListingDto.class);
    }

    private Listing convertToEntity(ListingDto listingDto) {
        Listing listing = modelMapper.map(listingDto, Listing.class);

        if (listingDto.getDealerId() != null) {
            Dealer dealer = dealerService.findById(listingDto.getDealerId());
            listing.setDealer(dealer);
        } else {
            //TODO: handle error
        }

        // set created time and default state for new listing
        if (listingDto.getId() == null) {
            listing.setState(ListingState.draft);
            listing.setCreatedAt(System.currentTimeMillis());
        } else {
            Listing oldListing = listingService.findById(listingDto.getId());
            listing.setId(oldListing.getId());
        }


        return listing;
    }
}
