package com.karolrinc.restaurantsystem.controllers;

import com.karolrinc.restaurantsystem.mappers.ClientResourceAssembler;
import com.karolrinc.restaurantsystem.models.Client;
import com.karolrinc.restaurantsystem.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
    
    private final ClientService clientService;
    private final ClientResourceAssembler assembler;
    
    @Autowired
    public ClientController(ClientService clientService, ClientResourceAssembler assembler) {
        this.clientService = clientService;
        this.assembler = assembler;
    }
    
    @GetMapping
    public ResponseEntity<?> findAllClients(@RequestParam(name = "page", defaultValue = "1") int page,
                                            @RequestParam(name = "pageSize", defaultValue = "5") int pageSize,
                                            @RequestParam(name = "sort", defaultValue = "id") String sort,
                                            PagedResourcesAssembler<Client> assembler) {
        Page<Client> allClients = clientService.findAllClients(PageRequest.of(page, pageSize, Sort.by(sort)));
        return ResponseEntity.ok(assembler.toResource(allClients,
                                                      linkTo(methodOn(ClientController.class).findAllClients(page,
                                                                                                             pageSize,
                                                                                                             sort,
                                                                                                             assembler)).withSelfRel()));
    }
    
    
    @GetMapping(path = "/{id}")
    public ResponseEntity findClientById(@PathVariable long id) {
        Client client = clientService.findById(id);
        return ResponseEntity.ok(assembler.toResource(client));
    }
    
    @PostMapping
    public ResponseEntity<?> saveClient(@RequestBody Client client) throws URISyntaxException {
        Resource<Client> clientResource = assembler.toResource(clientService.saveClient(client));
        return ResponseEntity.created(new URI(clientResource.getId()
                                                            .expand()
                                                            .getHref()))
                             .body(clientResource);
    }
    
    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateClient(@PathVariable long id, @RequestBody Client client) throws URISyntaxException {
        Resource<Client> clientResource = assembler.toResource(clientService.updateClient(id, client));
        return ResponseEntity.created(new URI(clientResource.getId()
                                                            .expand()
                                                            .getHref()))
                             .body(clientResource);
    }
    
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteClient(@PathVariable long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent()
                             .build();
    }
    
    @GetMapping
    public ResponseEntity<?> findClientsByPhoneNumber(@RequestParam(name = "phoneNumber") String phoneNumber) {
        Client client = clientService.findByPhoneNumber(phoneNumber);
        return ResponseEntity.ok(assembler.toResource(client));
    }
}
