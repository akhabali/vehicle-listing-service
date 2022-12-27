package io.github.akhabali.dto;

import io.github.akhabali.model.ListingState;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Listing - a vehicle advertisement.
 * <p>
 * Listing can be in one of two possible states:
 * - published - available online
 * - draft - not available online
 */

@Getter
@Setter
@NoArgsConstructor
public class GetListingDto {

    private Long id;
    private Long dealerId;
    private String vehicle;
    private Double price;
    private ListingState state;

    public GetListingDto(Long dealerId, String vehicle, Double price) {
        this.dealerId = dealerId;
        this.vehicle = vehicle;
        this.price = price;
        state = ListingState.draft;
    }
}
