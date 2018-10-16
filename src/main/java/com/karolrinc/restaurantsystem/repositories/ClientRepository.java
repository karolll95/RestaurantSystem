package com.karolrinc.restaurantsystem.repositories;

import com.karolrinc.restaurantsystem.models.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Page<Client> findAllByLastNameContainingIgnoreCase(String lastName, Pageable pageable);
    Optional<Client> findAllByFirstNameContainingIgnoreCaseAndLastNameContainingIgnoreCase(String firstName, String lastName);
}
