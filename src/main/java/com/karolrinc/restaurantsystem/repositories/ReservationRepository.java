package com.karolrinc.restaurantsystem.repositories;

import com.karolrinc.restaurantsystem.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
