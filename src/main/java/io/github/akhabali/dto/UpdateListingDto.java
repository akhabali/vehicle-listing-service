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
public class UpdateListingDto {

    private Long id;
    private String vehicle;
    private Double price;


    public UpdateListingDto(Long id, String vehicle, Double price) {
        this.id = id;
        this.vehicle = vehicle;
        this.price = price;
    }
}
