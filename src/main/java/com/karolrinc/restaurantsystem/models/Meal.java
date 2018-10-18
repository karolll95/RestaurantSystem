package com.karolrinc.restaurantsystem.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "menu", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class Meal {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    @NotBlank
    private String name;
    
    private BigDecimal price;
    
    @ManyToOne
    private Menu menu;
}
