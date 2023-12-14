package com.geektrust.backend.exceptions;

public class NoDriversAvailableException extends RuntimeException {

    public NoDriversAvailableException() 
    {
        super();
    }

    public NoDriversAvailableException(String msg) 
    {
        super(msg);
    }
    
}
