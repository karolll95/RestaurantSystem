package com.karolrinc.restaurantsystem.repositories;

import com.karolrinc.restaurantsystem.models.RestaurantTable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, Long> {
    Page<RestaurantTable> findAllByPersonAmount(int personAmount, Pageable pageable);
}
