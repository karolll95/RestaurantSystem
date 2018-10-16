package com.karolrinc.restaurantsystem.repositories;

import com.karolrinc.restaurantsystem.models.Client;
import com.karolrinc.restaurantsystem.models.Reservation;
import com.karolrinc.restaurantsystem.models.RestaurantTable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Page<Reservation> findAllByClient(Client client, Pageable pageable);
    Page<Reservation> findAllByRestaurantTable(RestaurantTable restaurantTable, Pageable pageable);
}
