package com.karolrinc.restaurantsystem.models;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.persistence.Table;

@Data
@Builder
@Entity
@Table(name = "reservations", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne
    private RestaurantTable restaurantTable;

    @OneToOne
    Client client;
}
