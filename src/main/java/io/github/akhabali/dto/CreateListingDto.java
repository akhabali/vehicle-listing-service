package io.github.akhabali.dto;

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
public class CreateListingDto {

    private String vehicle;
    private Double price;


    public CreateListingDto(String vehicle, Double price) {
        this.vehicle = vehicle;
        this.price = price;
    }
}
