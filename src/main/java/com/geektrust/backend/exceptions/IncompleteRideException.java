package com.geektrust.backend.exceptions;

public class IncompleteRideException extends RuntimeException {
    public IncompleteRideException() 
    {
        super();
    }

    public IncompleteRideException(String msg)
    {
        super(msg);
    }
    
}
