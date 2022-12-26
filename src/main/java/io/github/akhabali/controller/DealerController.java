package io.github.akhabali.controller;

import io.github.akhabali.model.Dealer;
import io.github.akhabali.service.DealerService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Dealer controller: The entry point to the operations authorized on a dealer
 */
@RestController
@RequestMapping("/dealers")
@RequiredArgsConstructor
public class DealerController {

    private final DealerService dealerService;

    /**
     * Look up a dealer by its ID
     *
     * @param id the identifier to lookup
     * @return dealer with 'id'
     * @throws io.github.akhabali.service.DealerNotFoundException when no dealer with 'id' is found
     */
    @Operation(summary = "Look up a dealer by its ID" )
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public EntityModel<Dealer> one(@PathVariable("id") Long id) {
        return dealerService.findById(id);
    }

    /**
     * Look up for all dealers
     *
     * @return the list of available dealers
     */
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public CollectionModel<EntityModel<Dealer>> all() {
        return dealerService.findAll();
    }

    /**
     * Create a new Dealer
     *
     * @param dealer dealer information to be used to create a dealer instance
     * @return the freshly created dealer
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EntityModel<Dealer>> newDealer(@RequestBody Dealer dealer) {
        return dealerService.newDealer(dealer);
    }

    /**
     * Delete dealer with 'id'
     *
     * @param id of dealer to be deleted
     * @return no content
     */
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return dealerService.delete(id);
    }
}
