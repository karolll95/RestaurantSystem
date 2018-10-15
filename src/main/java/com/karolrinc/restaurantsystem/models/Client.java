package com.karolrinc.restaurantsystem.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Data
@Entity
@Table(name = "clients", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class Client extends Person {

    @OneToOne
    Reservation reservation;
}
