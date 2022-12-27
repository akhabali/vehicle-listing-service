package io.github.akhabali.controller;

import io.github.akhabali.dto.GetListingDto;
import io.github.akhabali.model.Listing;
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
@RequestMapping("/listing")
@Tag(name = "Publish Listing", description = "Manage Listing state")
@RequiredArgsConstructor
public class PublishListingController {

    private final ModelMapper modelMapper;
    private final ListingService listingService;

    /**
     * Unpublish a vehicle listing for the dealer identified by 'dealer_id'
     * <p>
     * Change the listing state form published to draft
     *
     * @param listingId the id of the listing
     */
    @Operation(summary = "UnPublish a listing")
    @DeleteMapping(path = "/{listing_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unpublishListing(@PathVariable("listing_id") Long listingId) {
        listingService.unpublishListing(listingId);
    }

    /**
     * Publish a vehicle listing for the dealer identified by 'dealer_id'
     * <p>
     * Change the listing state form draft to published
     *
     * @param listingId    the id of the listing
     * @param forcePublish if true, publish a listing, but unpublish the oldest listing of a dealer to conform to the tier limit.
     */
    @Operation(summary = "Publish a listing")
    @PatchMapping(path = "/{listing_id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public GetListingDto publishListing(@PathVariable("listing_id") Long listingId, @RequestParam(name = "force", defaultValue = "false") boolean forcePublish) {
        return convertToDto(listingService.publishListing(listingId, forcePublish));
    }


    private GetListingDto convertToDto(Listing listing) {
        return modelMapper.map(listing, GetListingDto.class);
    }


}

