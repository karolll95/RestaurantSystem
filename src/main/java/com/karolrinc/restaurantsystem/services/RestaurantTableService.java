package com.karolrinc.restaurantsystem.services;

import com.karolrinc.restaurantsystem.exceptions.TableNotFoundException;
import com.karolrinc.restaurantsystem.models.RestaurantTable;
import com.karolrinc.restaurantsystem.repositories.RestaurantTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class RestaurantTableService {
    
    private final RestaurantTableRepository restaurantTableRepository;
    
    @Autowired
    public RestaurantTableService(RestaurantTableRepository restaurantTableRepository) {
        this.restaurantTableRepository = restaurantTableRepository;
    }
    
    public Page<RestaurantTable> findAllTables(int page, int pageSize, String sort) {
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
    
    public RestaurantTable findTableById(long id) {
        return restaurantTableRepository.findById(id)
                                        .orElseThrow(() -> new TableNotFoundException(id));
    }
    
    public Page<RestaurantTable> findTablesByPersonAmount(int personAmount, int page, int pageSize, String sort) {
        return restaurantTableRepository.findAllByPersonAmount(
                personAmount, PageRequest.of(page, pageSize, Sort.by(sort)));
    }
}
