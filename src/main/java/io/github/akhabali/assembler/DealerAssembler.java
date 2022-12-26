package io.github.akhabali.assembler;

import io.github.akhabali.controller.DealerController;
import io.github.akhabali.model.Dealer;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class DealerAssembler implements RepresentationModelAssembler<Dealer, EntityModel<Dealer>> {
    @Override
    public EntityModel<Dealer> toModel(Dealer employee) {
        return EntityModel.of(employee,
                linkTo(methodOn(DealerController.class).one(employee.getId())).withSelfRel(),
                linkTo(methodOn(DealerController.class).all()).withRel("dealers"));
    }

}
