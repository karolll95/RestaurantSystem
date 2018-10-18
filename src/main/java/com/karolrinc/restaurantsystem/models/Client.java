package com.karolrinc.restaurantsystem.models;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@Entity
@Table(name = "clients", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class Client extends Person {

    @OneToOne
    Reservation reservation;
}
