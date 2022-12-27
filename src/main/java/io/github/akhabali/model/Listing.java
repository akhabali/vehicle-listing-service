package io.github.akhabali.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Listing - a vehicle advertisement.
 * <p>
 * Listing can be in one of two possible states:
 * - published - available online
 * - draft - not available online
 */
@Entity
@Getter
@Setter
public class Listing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Dealer dealer;

    @NotBlank
    private String vehicle;

    @NotNull
    private Double price;

    @NotNull
    private Long createdAt;

    @Enumerated(EnumType.STRING)
    private ListingState state;
}
