package io.github.akhabali.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * Dealer Entity: the representation of a dealer in the database layer
 */
@Entity
@Getter
@Setter
public class Dealer {

    /**
     * Primary Key: Auto generated using an Identity Table
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @Min(0)
    private Long tierLimit;

}
