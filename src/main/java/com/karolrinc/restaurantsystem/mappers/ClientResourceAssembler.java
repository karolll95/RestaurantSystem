package com.karolrinc.restaurantsystem.mappers;

import com.karolrinc.restaurantsystem.controllers.ClientController;
import com.karolrinc.restaurantsystem.models.Client;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class ClientResourceAssembler implements ResourceAssembler<Client, Resource<Client>> {
    
    @Override
    public Resource<Client> toResource(Client client) {
        return new Resource<>(
                client, linkTo(methodOn(ClientController.class).findClientById(client.getId())).withSelfRel());
    }
}
