package io.github.akhabali.controller;

import io.github.akhabali.dto.CreateListingDto;
import io.github.akhabali.dto.DealerListingDto;
import io.github.akhabali.dto.GetListingDto;
import io.github.akhabali.dto.UpdateListingDto;
import io.github.akhabali.errors.DealerNotFoundException;
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

import java.util.stream.Collectors;

/**
 * Listing controller: The entry point to the operations authorized on a dealer listing
 */
@Log4j2
@RestController
@RequestMapping("/dealers/{dealer_id}")
@Tag(name = "Dealer Listing", description = "Manage vehicle listing of a dealer")
@RequiredArgsConstructor
public class DealerListingController {

    private final ModelMapper modelMapper;

    private final DealerService dealerService;
    private final ListingService listingService;


    /**
     * Create a new vehicle listing for the dealer identified by 'dealer_id'
     * <p>
     * All the created listings should have state draft by default;
     *
     * @param dealerId the id of the dealer
     * @param listing  vehicle information to be used to create the listing
     * @return the freshly created listing
     */
    @Operation(summary = "Create a new vehicle listing for a dealer")
    @PostMapping(path = "/listing", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public GetListingDto newListing(@PathVariable("dealer_id") Long dealerId, @RequestBody CreateListingDto listing) {
        return convertToDto(listingService.createListing(convertToEntity(dealerId, listing)));
    }

    /**
     * Update a vehicle listing for the dealer identified by 'dealer_id'
     *
     * @param dealerId the id of the dealer
     * @param listing  vehicle information to be used to create the listing
     */
    @Operation(summary = "Update a vehicle listing of a dealer")
    @PatchMapping(path = "/listing", produces = MediaType.APPLICATION_JSON_VALUE)
    public GetListingDto updateListing(@PathVariable("dealer_id") Long dealerId, @RequestBody UpdateListingDto listing) {
        return convertToDto(listingService.updateListing(convertToEntity(dealerId, listing)));
    }

    /**
     * Get all listings of a dealer with a given state
     * <p>
     * All the created listings should have state draft by default;
     *
     * @param dealerId the id of the dealer
     * @param state    listing state
     * @return the freshly created listing
     */
    @Operation(summary = "Get all vehicle listings of a dealer with a given state")
    @GetMapping(path = "/listing", produces = MediaType.APPLICATION_JSON_VALUE)
    public DealerListingDto findListingByDealerId(@PathVariable("dealer_id") Long dealerId, @RequestParam("state") ListingState state) {
        return new DealerListingDto(listingService.findListingByDealerId(dealerId, state)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList()));
    }

    private GetListingDto convertToDto(Listing listing) {
        return modelMapper.map(listing, GetListingDto.class);
    }

    private Listing convertToEntity(Long dealerId, CreateListingDto listingDto) {
        Listing listing = modelMapper.map(listingDto, Listing.class);

        if (dealerId == null) {
            throw new DealerNotFoundException(0L); // TODO: create a specific error if a different message is needed
        }

        final Dealer dealer = dealerService.findById(dealerId);
        listing.setDealer(dealer);

        return listing;
    }

    private Listing convertToEntity(Long dealerId, UpdateListingDto listingDto) {
        Listing listing = modelMapper.map(listingDto, Listing.class);

        if (dealerId == null) {
            throw new DealerNotFoundException(0L); // TODO: create a specific error if a different message is needed
        }
        final Dealer dealer = dealerService.findById(dealerId);
        listing.setDealer(dealer);

        if (listingDto.getId() != null) {
            Listing oldListing = listingService.findById(listingDto.getId());
            listing.setId(oldListing.getId());
            listing.setCreatedAt(oldListing.getCreatedAt());
        }

        return listing;
    }

}
