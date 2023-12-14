package com.geektrust.backend.exceptions;

public class DriverAlreadyExistsException extends RuntimeException {

    public DriverAlreadyExistsException()
    {
        super();
    }

    public  DriverAlreadyExistsException(String msg) 
    {
        super(msg);
    }
    
}
