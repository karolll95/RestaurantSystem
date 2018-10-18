package com.karolrinc.restaurantsystem.enums;

public enum Status {
    IN_PROGRESS("in progress"), COMPLETED("completed"), CANCELLED("canceled");
    String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus(){
        return status;
    }
}
