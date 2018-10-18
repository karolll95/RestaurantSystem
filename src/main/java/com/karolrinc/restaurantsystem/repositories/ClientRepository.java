package com.karolrinc.restaurantsystem.repositories;

import com.karolrinc.restaurantsystem.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findAllByFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCase(String firstName, String lastName);
    Optional<Client> findAllByPhoneNumber(String phoneNumber);
}
