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
    
    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }
    
    public void deleteClient(long id) {
        clientRepository.deleteById(id);
    }
    
    public Page<Client> findAllClients(Pageable pageable) {
        return clientRepository.findAll(pageable);
    }
    
    public Client updateClient(long id, Client newClient) {
        return clientRepository.findById(id)
                               .map(client -> {
                                   client.setFirstName(newClient.getFirstName());
                                   client.setLastName(newClient.getLastName());
                                   client.setPhoneNumber(newClient.getPhoneNumber());
                                   client.setReservation(newClient.getReservation());
                                   return clientRepository.save(client);
                               })
                               .orElseGet(() -> {
                                   newClient.setId(id);
                                   return clientRepository.save(newClient);
                               });
    }
    
    public Client findByFullName(String firstName, String lastName) {
        return clientRepository.findAllByFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCase(
                firstName, lastName)
                               .orElseThrow(() -> new ClientNotFoundException(firstName + " " + lastName));
    }
    
    public Client findById(long id) {
        return clientRepository.findById(id)
                               .orElseThrow(() -> new ClientNotFoundException(id));
    }
    
    public Client findByPhoneNumber(String phoneNumber) {
        return clientRepository.findAllByPhoneNumber(phoneNumber)
                               .orElseThrow(() -> new ClientNotFoundException(phoneNumber));
    }
}
