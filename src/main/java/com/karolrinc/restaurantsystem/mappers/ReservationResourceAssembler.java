package com.karolrinc.restaurantsystem.mappers;

import com.karolrinc.restaurantsystem.controllers.ReservationController;
import com.karolrinc.restaurantsystem.enums.Status;
import com.karolrinc.restaurantsystem.models.Reservation;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class ReservationResourceAssembler implements ResourceAssembler<Reservation, Resource<Reservation>> {
    
    @Override
    public Resource<Reservation> toResource(Reservation reservation) {
        Resource<Reservation> reservationResource = new Resource<>(reservation, linkTo(methodOn(
                ReservationController.class).findReservationById(reservation.getId()))
                .withSelfRel()
        );
        
        if (reservation.getStatus()
                       .equals(Status.IN_PROGRESS)) {
            reservationResource.add(
                    linkTo(methodOn(ReservationController.class).cancelReservation(reservation.getId())).withSelfRel());
            reservationResource.add(linkTo(methodOn(ReservationController.class).completeReservation(
                    reservation.getId())).withSelfRel());
        }
        return reservationResource;
    }
}
