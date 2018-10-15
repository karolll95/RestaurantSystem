package com.karolrinc.restaurantsystem.repositories;

import com.karolrinc.restaurantsystem.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
