package com.karolrinc.restaurantsystem.exceptions;

public class TableNotFoundException extends RuntimeException {
    
    public TableNotFoundException(long id) {
        super("Table with id: " + id + " not found.");
    }
}
