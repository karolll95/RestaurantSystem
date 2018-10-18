package com.karolrinc.restaurantsystem.exceptions;

public class ClientNotFoundException extends RuntimeException {
    public ClientNotFoundException(long id) {
        super("Client with id: " + id + " not found.");
    }
    
    public ClientNotFoundException(String name) {
        super("Client " + name + " not found.");
    }
}
