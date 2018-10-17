package com.karolrinc.restaurantsystem.mappers;

import com.karolrinc.restaurantsystem.controllers.ReservationController;
import com.karolrinc.restaurantsystem.models.Reservation;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class ReservationResourceAssembler implements ResourceAssembler<Reservation, Resource<Reservation>> {

    @Override
    public Resource<Reservation> toResource(Reservation reservation) {
        return new Resource<>(reservation,
                              linkTo(methodOn(ReservationController.class).findReservationById(reservation.getId())).withSelfRel());

    }
}
