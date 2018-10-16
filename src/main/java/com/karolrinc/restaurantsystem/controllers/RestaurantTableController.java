package com.karolrinc.restaurantsystem.controllers;

import com.karolrinc.restaurantsystem.models.RestaurantTable;
import com.karolrinc.restaurantsystem.services.RestaurantTableService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tables")
public class RestaurantTableController {

    private final RestaurantTableService restaurantTableService;

    @Autowired
    public RestaurantTableController(RestaurantTableService restaurantTableService) {
        this.restaurantTableService = restaurantTableService;
    }

    @GetMapping
    public ResponseEntity<?> getAllTables(@RequestParam(name = "page", defaultValue = "1") int page,
                                          @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                          @RequestParam(name = "sort", defaultValue = "id") String sort,
                                          @RequestParam(name = "personAmount", defaultValue = "") String personAmount) {
        if (!personAmount.isEmpty()) {
            return ResponseEntity.ok(restaurantTableService.getTablesByPersonAmount(Integer.valueOf(personAmount), page, pageSize, sort));
        } else {
            return ResponseEntity.ok(restaurantTableService.getAllTables(page, pageSize, sort));
        }
    }

    @PostMapping
    public ResponseEntity<?> saveTable(@RequestBody RestaurantTable restaurantTable) {
        return new ResponseEntity<>(restaurantTableService.saveTable(restaurantTable), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity updateTable(@RequestBody RestaurantTable restaurantTable) {
        return ResponseEntity.ok(restaurantTableService.updateTable(restaurantTable));
    }

    @DeleteMapping(path = "/{id}")
    public void deleteTable(@PathVariable long id) {
        restaurantTableService.deleteTable(id);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getTableById(@PathVariable long id) {
        try {
            return ResponseEntity.ok(restaurantTableService.getTableById(id));
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
