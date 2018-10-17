package com.karolrinc.restaurantsystem.services;

import com.karolrinc.restaurantsystem.exceptions.ReservationNotFoundException;
import com.karolrinc.restaurantsystem.models.Client;
import com.karolrinc.restaurantsystem.models.Reservation;
import com.karolrinc.restaurantsystem.models.RestaurantTable;
import com.karolrinc.restaurantsystem.repositories.ReservationRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation saveReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    public Page<Reservation> findAllReservations(Pageable pageable) {
        return reservationRepository.findAll(pageable);
    }

    public Reservation findReservationById(long id) {
        return reservationRepository.findById(id).orElseThrow(() -> new ReservationNotFoundException(id));
    }

    public Reservation updateReservation(long id, Reservation newReservation) {
        return reservationRepository.findById(id)
                                    .map(reservation -> {
                                        reservation.setRestaurantTable(newReservation.getRestaurantTable());
                                        reservation.setClient(newReservation.getClient());
                                        reservation.setStatus(newReservation.getStatus());
                                        return saveReservation(reservation);
                                    })
                                    .orElseGet(() -> {
                                        newReservation.setId(id);
                                        return saveReservation(newReservation);
                                    });
    }

    public void deleteReservation(long id) {
        reservationRepository.deleteById(id);
    }

    public Page<Reservation> findReservationsByClient(Client client, Pageable pageable) {
        return reservationRepository.findAllByClient(client, pageable);
    }

    public Page<Reservation> findReservationsByTable(RestaurantTable table, Pageable pageable) {
        return reservationRepository.findAllByRestaurantTable(table, pageable);
    }
}
