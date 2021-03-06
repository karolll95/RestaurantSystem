package com.karolrinc.restaurantsystem.controllers;

import com.karolrinc.restaurantsystem.enums.Status;
import com.karolrinc.restaurantsystem.mappers.ReservationResourceAssembler;
import com.karolrinc.restaurantsystem.models.Client;
import com.karolrinc.restaurantsystem.models.Reservation;
import com.karolrinc.restaurantsystem.models.RestaurantTable;
import com.karolrinc.restaurantsystem.services.ClientService;
import com.karolrinc.restaurantsystem.services.ReservationService;
import com.karolrinc.restaurantsystem.services.RestaurantTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {
    
    private final ReservationService reservationService;
    private final ClientService clientService;
    private final RestaurantTableService restaurantTableService;
    private final ReservationResourceAssembler assembler;
    
    @Autowired
    public ReservationController(ReservationService reservationService, ClientService clientService,
                                 RestaurantTableService restaurantTableService,
                                 ReservationResourceAssembler assembler) {
        this.reservationService = reservationService;
        this.clientService = clientService;
        this.restaurantTableService = restaurantTableService;
        this.assembler = assembler;
    }
    
    @GetMapping
    public ResponseEntity<?> findAllReservations(@RequestParam(name = "page", defaultValue = "1") int page,
                                                 @RequestParam(name = "pageSize", defaultValue = "5") int pageSize,
                                                 @RequestParam(name = "sort", defaultValue = "id") String sort,
                                                 PagedResourcesAssembler<Reservation> assembler) {
        
        Page<Reservation> reservations = reservationService.findAllReservations(
                PageRequest.of(page, pageSize, Sort.by(sort)));
        return ResponseEntity.ok(assembler.toResource(reservations,
                                                      linkTo(methodOn(ReservationController.class).findAllReservations(
                                                              page, pageSize, sort, assembler)).withSelfRel()));
    }
    
    @PostMapping
    public ResponseEntity<?> saveReservation(@RequestBody Reservation reservation) throws URISyntaxException {
        Resource<Reservation> reservationResource = assembler.toResource(
                reservationService.saveReservation(reservation));
        return ResponseEntity.created(new URI(reservationResource.getId()
                                                                 .expand()
                                                                 .getHref()))
                             .body(reservationResource);
    }
    
    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateReservation(@PathVariable long id,
                                               @RequestBody Reservation reservation) throws URISyntaxException {
        Resource<Reservation> reservationResource = assembler.toResource(
                reservationService.updateReservation(id, reservation));
        return ResponseEntity.created(new URI(reservationResource.getId()
                                                                 .expand()
                                                                 .getHref()))
                             .body(reservationResource);
    }
    
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteReservation(@PathVariable long id) {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent()
                             .build();
    }
    
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> findReservationById(@PathVariable long id) {
        Reservation reservation = reservationService.findReservationById(id);
        return ResponseEntity.ok(assembler.toResource(reservation));
    }
    
    @GetMapping(path = "/clients/{id}")
    public ResponseEntity<?> findReservationByClient(@PathVariable long id,
                                                     @RequestParam(name = "page", defaultValue = "1") int page,
                                                     @RequestParam(name = "pageSize", defaultValue = "5") int pageSize,
                                                     @RequestParam(name = "sort", defaultValue = "id") String sort,
                                                     PagedResourcesAssembler<Reservation> assembler) {
        
        Client client = clientService.findById(id);
        Page<Reservation> reservationPage = reservationService.findReservationsByClient(
                client, PageRequest.of(page, pageSize, Sort.by(sort)));
        return ResponseEntity.ok(assembler.toResource(reservationPage, linkTo(methodOn(
                ReservationController.class).findReservationByClient(id, page, pageSize, sort,
                                                                     assembler)).withSelfRel()));
    }
    
    @GetMapping(path = "/clients")
    public ResponseEntity<?> findReservationByClient(@RequestParam(name = "lastName") String lastName,
                                                     @RequestParam(name = "firstName") String firstName,
                                                     @RequestParam(name = "page", defaultValue = "1") int page,
                                                     @RequestParam(name = "pageSize", defaultValue = "5") int pageSize,
                                                     @RequestParam(name = "sort", defaultValue = "id") String sort,
                                                     PagedResourcesAssembler<Reservation> assembler) {
        
        Client client = clientService.findByFullName(firstName, lastName);
        Page<Reservation> reservationPage = reservationService.findReservationsByClient(
                client, PageRequest.of(page, pageSize, Sort.by(sort)));
        return ResponseEntity.ok(assembler.toResource(reservationPage, linkTo(methodOn(
                ReservationController.class).findReservationByClient(lastName, firstName, page, pageSize, sort,
                                                                     assembler)).withSelfRel()));
        
    }
    
    @GetMapping(path = "tables/{id}")
    public ResponseEntity<?> findReservationByTable(@PathVariable long id,
                                                    @RequestParam(name = "page", defaultValue = "1") int page,
                                                    @RequestParam(name = "pageSize", defaultValue = "5") int pageSize,
                                                    @RequestParam(name = "sort", defaultValue = "id") String sort,
                                                    PagedResourcesAssembler<Reservation> assembler) {
        
        RestaurantTable table = restaurantTableService.findTableById(id);
        Page<Reservation> reservationPage = reservationService.findReservationsByTable(
                table, PageRequest.of(page, pageSize, Sort.by(sort)));
        return ResponseEntity.ok(assembler.toResource(reservationPage, linkTo(methodOn(
                ReservationController.class).findReservationByTable(id, page, pageSize, sort,
                                                                    assembler)).withSelfRel()));
        
    }
    
    @DeleteMapping(path = "/{id}/cancel")
    public ResponseEntity<?> cancelReservation(@PathVariable long id) {
        Reservation reservation = reservationService.findReservationById(id);
        
        if (reservation.getStatus()
                       .equals(Status.IN_PROGRESS)) {
            reservation.setStatus(Status.CANCELLED);
            return ResponseEntity.ok(assembler.toResource(reservationService.saveReservation(reservation)));
        } else {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                                 .body(new VndErrors.VndError(
                                         "Method not allowed",
                                         "You can't cancel an order that is in the " + reservation.getStatus() + " status."));
        }
    }
    
    @PutMapping(path = "/{id}/complete")
    public ResponseEntity completeReservation(@PathVariable long id) {
        Reservation reservation = reservationService.findReservationById(id);
        
        if (reservation.getStatus()
                       .equals(Status.IN_PROGRESS)) {
            reservation.setStatus(Status.COMPLETED);
            return ResponseEntity.ok(assembler.toResource(reservationService.saveReservation(reservation)));
        } else {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                                 .body(new VndErrors.VndError(
                                         "Method not allowed",
                                         "You can't complete an order that is in the " + reservation.getStatus() + " status."));
        }
    }
}
