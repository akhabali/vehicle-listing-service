package io.github.akhabali.controller;

import io.github.akhabali.dto.DealerDto;
import io.github.akhabali.dto.DealerListDto;
import io.github.akhabali.errors.DealerNotFoundException;
import io.github.akhabali.model.Dealer;
import io.github.akhabali.service.DealerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Dealer controller: The entry point to the operations authorized on a dealer
 */
@Log4j2
@RestController
@RequestMapping("/dealers")
@Tag(name = "Dealer", description = "Dealer Service")
@RequiredArgsConstructor
public class DealerController {

    private final DealerService dealerService;

    private final ModelMapper modelMapper;

    /**
     * Look up a dealer by its ID
     *
     * @param id the identifier to lookup
     * @return dealer with 'id'
     * @throws DealerNotFoundException when no dealer with 'id' is found
     */
    @Operation(summary = "Look up a dealer by its ID")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public DealerDto findById(@PathVariable("id") Long id) {
        log.trace("searching for dealer with id=" + id);
        Dealer dealer = dealerService.findById(id);
        return convertToDto(dealer);
    }


    /**
     * Look up for all dealers
     *
     * @return the list of available dealers
     */
    @Operation(summary = "Look up for all dealers")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public DealerListDto findAll() {
        // TODO: add pagination to this service
        log.trace("getting all dealers");
        List<Dealer> dealers = dealerService.findAll();
        return new DealerListDto(dealers.stream().map(this::convertToDto).collect(Collectors.toList()));
    }

    /**
     * Create a new Dealer
     *
     * @param dealer dealer information to be used to create a dealer instance
     * @return the freshly created dealer
     */
    @Operation(summary = "Create a new Dealer")
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public DealerDto createDealer(@RequestBody DealerDto dealer) {
        log.trace("Creating a new dealer with info=" + dealer);
        Dealer newDealer = dealerService.newDealer(convertToEntity(dealer));
        return convertToDto(newDealer);
    }

    /**
     * Delete dealer with 'id'
     *
     * @param id of dealer to be deleted
     */
    @Operation(summary = "Delete dealer with 'id'")
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        log.trace("Deleting dealer with id=" + id);
        dealerService.delete(id);
    }

    private DealerDto convertToDto(Dealer dealer) {
        return modelMapper.map(dealer, DealerDto.class);
    }

    private Dealer convertToEntity(DealerDto dealerDto) {
        Dealer dealer = modelMapper.map(dealerDto, Dealer.class);
        if (dealerDto.getId() != null) {
            Dealer oldDealer = dealerService.findById(dealerDto.getId());
            dealer.setId(oldDealer.getId());
        }
        return dealer;
    }
}
