package com.karolrinc.restaurantsystem.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.List;

@Data
@Entity
@Table(name = "menu", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class Menu {
    
    @OneToMany(mappedBy = "menu")
    private List<Meal> meals;
}
