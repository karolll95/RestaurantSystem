package com.karolrinc.restaurantsystem.services;

import com.karolrinc.restaurantsystem.exceptions.ClientNotFoundException;
import com.karolrinc.restaurantsystem.models.Client;
import com.karolrinc.restaurantsystem.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Page findByLastName(String lastName, Pageable pageable) {
        return clientRepository.findAllByLastNameContainingIgnoreCase(lastName, pageable);
    }

    public Client findByFullName(String firstName, String lastName) {
        return clientRepository.findAllByFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCase(firstName, lastName)
                .orElseThrow(() -> new ClientNotFoundException(firstName + " " + lastName));
    }

    public Client findById(long id) {
        return clientRepository.findById(id).orElseThrow(() -> new ClientNotFoundException(id));
    }
}
