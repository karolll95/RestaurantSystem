package com.karolrinc.restaurantsystem.controllers;

import com.karolrinc.restaurantsystem.mappers.ReservationResourceAssembler;
import com.karolrinc.restaurantsystem.models.Client;
import com.karolrinc.restaurantsystem.models.Reservation;
import com.karolrinc.restaurantsystem.models.RestaurantTable;
import com.karolrinc.restaurantsystem.services.ClientService;
import com.karolrinc.restaurantsystem.services.ReservationService;
import com.karolrinc.restaurantsystem.services.RestaurantTableService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final ClientService clientService;
    private final RestaurantTableService restaurantTableService;
    private final ReservationResourceAssembler reservationResourceAssembler;

    @Autowired
    public ReservationController(ReservationService reservationService, ClientService clientService, RestaurantTableService restaurantTableService, ReservationResourceAssembler reservationResourceAssembler) {
        this.reservationService = reservationService;
        this.clientService = clientService;
        this.restaurantTableService = restaurantTableService;
        this.reservationResourceAssembler = reservationResourceAssembler;
    }

    @GetMapping
    public ResponseEntity<?> findAllReservations(@RequestParam(name = "page", defaultValue = "1") int page,
                                                 @RequestParam(name = "pageSize", defaultValue = "5") int pageSize,
                                                 @RequestParam(name = "sort", defaultValue = "id") String sort,
                                                 PagedResourcesAssembler<Reservation> assembler) {
        Page<Reservation> reservations = reservationService.findAllReservations(PageRequest.of(page, pageSize, Sort.by(sort)));
        return ResponseEntity.ok(assembler.toResource(
                reservations,
                linkTo(methodOn(ReservationController.class).findAllReservations(page, pageSize, sort, assembler)).withSelfRel()
        ));

    }

    @PostMapping
    public ResponseEntity<?> saveReservation(@RequestBody Reservation reservation) {
        return new ResponseEntity<>(reservationService.saveReservation(reservation), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<?> updateReservation(@RequestBody Reservation reservation) {
        return ResponseEntity.ok(reservationService.updateReservation(reservation));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteReservation(@PathVariable long id) {
        reservationService.deleteReservation(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> findReservationById(@PathVariable long id) {
        try {
            Reservation reservation = reservationService.findReservationById(id);
            return ResponseEntity.ok(reservationResourceAssembler.toResource(reservation));
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/clients/{id}")
    public ResponseEntity<?> findReservationByClient(@PathVariable long id,
                                                     @RequestParam(name = "page", defaultValue = "1") int page,
                                                     @RequestParam(name = "pageSize", defaultValue = "5") int pageSize,
                                                     @RequestParam(name = "sort", defaultValue = "id") String sort,
                                                     PagedResourcesAssembler<Reservation> assembler) {
        try {
            Client client = clientService.findById(id);
            Page<Reservation> reservationPage = reservationService.findReservationsByClient(client, PageRequest.of(page, pageSize, Sort.by(sort)));
            return ResponseEntity.ok(assembler.toResource(reservationPage, linkTo(methodOn(ReservationController.class)
                                                                                          .findReservationByClient(id, page, pageSize, sort, assembler))
                    .withSelfRel()));
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/clients")
    public ResponseEntity<?> findReservationByClient(@RequestParam(name = "lastName") String lastName,
                                                     @RequestParam(name = "firstName") String firstName,
                                                     @RequestParam(name = "page", defaultValue = "1") int page,
                                                     @RequestParam(name = "pageSize", defaultValue = "5") int pageSize,
                                                     @RequestParam(name = "sort", defaultValue = "id") String sort,
                                                     PagedResourcesAssembler<Reservation> assembler) {
        try {
            Client client = clientService.findByFullName(firstName, lastName);
            Page<Reservation> reservationPage = reservationService.findReservationsByClient(client, PageRequest.of(page, pageSize, Sort.by(sort)));
            return ResponseEntity.ok(assembler.toResource(reservationPage,linkTo(methodOn(ReservationController.class)
                                                                                           .findReservationByClient(lastName, firstName, page, pageSize, sort, assembler))
                    .withSelfRel()));

        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "tables/{id}")
    public ResponseEntity<?> findReservationByTable(@PathVariable long id,
                                                    @RequestParam(name = "page", defaultValue = "1") int page,
                                                    @RequestParam(name = "pageSize", defaultValue = "5") int pageSize,
                                                    @RequestParam(name = "sort", defaultValue = "id") String sort,
                                                    PagedResourcesAssembler<Reservation> assembler) {
        try {
            RestaurantTable table = restaurantTableService.findTableById(id);
            Page<Reservation> reservationPage = reservationService.findReservationsByTable(table, PageRequest.of(page, pageSize, Sort.by(sort)));
            return ResponseEntity.ok(assembler.toResource(reservationPage, linkTo(methodOn(ReservationController.class)
                                                                                          .findReservationByTable(id, page, pageSize, sort, assembler)).withSelfRel()));
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
