package com.karolrinc.restaurantsystem.models;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Table(name = "orders", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    private RestaurantTable table;
    private List<Meal> meals;
    private BigDecimal finalPrize;
}
