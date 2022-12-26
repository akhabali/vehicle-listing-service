package io.github.akhabali.service;

import io.github.akhabali.assembler.DealerAssembler;
import io.github.akhabali.controller.DealerController;
import io.github.akhabali.model.Dealer;
import io.github.akhabali.repository.DealerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Dealer Service: responsible for orchestrating data access and data transformation
 */
@Service
@RequiredArgsConstructor
public class DealerService {


    private final DealerRepository dealerRepository;
    private final DealerAssembler dealerAssembler;

    public EntityModel<Dealer> findById(Long id) {
        Dealer dealer = dealerRepository.findById(id).orElseThrow(() -> new DealerNotFoundException(id));
        return dealerAssembler.toModel(dealer);
    }

    public CollectionModel<EntityModel<Dealer>> findAll() {
        List<EntityModel<Dealer>> dealers = dealerRepository.findAll().stream().map(dealerAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(dealers,
                linkTo(methodOn(DealerController.class).all()).withSelfRel());

    }

    public ResponseEntity<EntityModel<Dealer>> newDealer(Dealer dealer) {
        EntityModel<Dealer> entityModel = dealerAssembler.toModel(this.dealerRepository.save(dealer));
        Link location = entityModel.getRequiredLink(IanaLinkRelations.SELF);

        return ResponseEntity.created(location.toUri())
                .body(entityModel);
    }

    public ResponseEntity<?> delete(Long id) {
        this.dealerRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
