package io.github.akhabali.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private String name;
    private Long tierLimit;

}
