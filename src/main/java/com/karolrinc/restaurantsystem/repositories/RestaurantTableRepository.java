package com.karolrinc.restaurantsystem.repositories;

import com.karolrinc.restaurantsystem.models.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, Long> {
}
