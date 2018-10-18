package com.karolrinc.restaurantsystem.mappers;

import com.karolrinc.restaurantsystem.controllers.RestaurantTableController;
import com.karolrinc.restaurantsystem.models.RestaurantTable;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class RestaurantTableResourceAssembler implements ResourceAssembler<RestaurantTable, Resource<RestaurantTable>> {
    
    @Override
    public Resource<RestaurantTable> toResource(RestaurantTable restaurantTable) {
        return new Resource<>(restaurantTable, linkTo(methodOn(RestaurantTableController.class).findTableById(
                restaurantTable.getId())).withSelfRel());
    }
}
