package com.karolrinc.restaurantsystem.services;

import com.karolrinc.restaurantsystem.models.RestaurantTable;
import com.karolrinc.restaurantsystem.repositories.RestaurantTableRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RestaurantTableService {

    private final RestaurantTableRepository restaurantTableRepository;

    @Autowired
    public RestaurantTableService(RestaurantTableRepository restaurantTableRepository) {
        this.restaurantTableRepository = restaurantTableRepository;
    }

    public Page getAllTables(int page, int pageSize, String sort) {
        return restaurantTableRepository.findAll(PageRequest.of(page, pageSize, Sort.by(sort)));
    }

    public RestaurantTable saveTable(RestaurantTable restaurantTable) {
        return restaurantTableRepository.save(restaurantTable);
    }

    public void deleteTable(long id) {
        restaurantTableRepository.deleteById(id);
    }

    public RestaurantTable updateTable(RestaurantTable restaurantTable) {
        return restaurantTableRepository.save(restaurantTable);
    }

    public RestaurantTable getTableById(long id) throws NotFoundException {
        Optional<RestaurantTable> table = restaurantTableRepository.findById(id);
        return table.orElseThrow(() -> new NotFoundException("Table with id: " + id + "not found."));
    }

    public Page getTablesByPersonAmount(int personAmount, int page, int pageSize, String sort) {
        return restaurantTableRepository.findAllByPersonAmount(personAmount, PageRequest.of(page, pageSize, Sort.by(sort)));
    }
}
