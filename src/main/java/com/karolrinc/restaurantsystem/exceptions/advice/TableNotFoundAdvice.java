package com.karolrinc.restaurantsystem.exceptions.advice;

import com.karolrinc.restaurantsystem.exceptions.TableNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class TableNotFoundAdvice {
    
    @ResponseBody
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String tableNotFoundHandler(TableNotFoundException e) {
        return e.getMessage();
    }
}
