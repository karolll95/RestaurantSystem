package com.karolrinc.restaurantsystem.models;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "tables", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class RestaurantTable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private int personAmount;

    @OneToOne
    private Reservation reservation;

}
