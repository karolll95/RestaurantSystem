package com.karolrinc.restaurantsystem.controllers;

import com.karolrinc.restaurantsystem.mappers.RestaurantTableResourceAssembler;
import com.karolrinc.restaurantsystem.models.RestaurantTable;
import com.karolrinc.restaurantsystem.services.RestaurantTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/tables")
public class RestaurantTableController {
    
    private final RestaurantTableService restaurantTableService;
    private final RestaurantTableResourceAssembler assembler;
    
    @Autowired
    public RestaurantTableController(RestaurantTableService restaurantTableService, RestaurantTableResourceAssembler assembler) {
        this.restaurantTableService = restaurantTableService;
        this.assembler = assembler;
    }
    
    @GetMapping
    public ResponseEntity<?> findAllTables(@RequestParam(name = "page", defaultValue = "1") int page,
                                           @RequestParam(name = "pageSize", defaultValue = "10") int pageSize,
                                           @RequestParam(name = "sort", defaultValue = "id") String sort,
                                           PagedResourcesAssembler<RestaurantTable> assembler) {
        Page<RestaurantTable> tablesPage = restaurantTableService.findAllTables(page, pageSize, sort);
        return ResponseEntity.ok(assembler.toResource(tablesPage,
                                                      linkTo(methodOn(RestaurantTableController.class).findAllTables(
                                                              page, pageSize, sort, assembler)).withSelfRel()));
    }
    
    @PostMapping
    public ResponseEntity<?> saveTable(@RequestBody RestaurantTable restaurantTable) throws URISyntaxException {
        Resource<RestaurantTable> tableResource = assembler.toResource(
                restaurantTableService.saveTable(restaurantTable));
        return ResponseEntity.created(new URI(tableResource.getId()
                                                           .expand()
                                                           .getHref()))
                             .body(tableResource);
    }
    
    @PutMapping
    public ResponseEntity<?> updateTable(@RequestBody RestaurantTable restaurantTable) throws URISyntaxException {
        Resource<RestaurantTable> tableResource = assembler.toResource(
                restaurantTableService.updateTable(restaurantTable));
        return ResponseEntity.created(new URI(tableResource.getId()
                                                           .expand()
                                                           .getHref()))
                             .body(tableResource);
    }
    
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteTable(@PathVariable long id) {
        restaurantTableService.deleteTable(id);
        return ResponseEntity.noContent()
                             .build();
    }
    
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> findTableById(@PathVariable long id) {
        Resource<RestaurantTable> tableResource = assembler.toResource(restaurantTableService.findTableById(id));
        return ResponseEntity.ok(tableResource);
    }
    
    @GetMapping
    public ResponseEntity<?> findTablesByPersonAmount(@RequestParam(name = "page", defaultValue = "1") int page,
                                                      @RequestParam(name = "pageSize",
                                                                    defaultValue = "10") int pageSize,
                                                      @RequestParam(name = "sort", defaultValue = "id") String sort,
                                                      @RequestParam(name = "personAmount") String personAmount,
                                                      PagedResourcesAssembler<RestaurantTable> assembler) {
        
        Page<RestaurantTable> tablesPage = restaurantTableService.findTablesByPersonAmount(
                Integer.valueOf(personAmount), page, pageSize, sort);
        return ResponseEntity.ok(assembler.toResource(tablesPage, linkTo(methodOn(
                RestaurantTableController.class).findTablesByPersonAmount(page, pageSize, sort, personAmount,
                                                                          assembler)).withSelfRel()));
    }
}
