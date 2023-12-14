package com.geektrust.backend.exceptions;

public class RideNotValidException extends RuntimeException {
    public RideNotValidException()
    {
     super();
    }
    public RideNotValidException(String msg)
    {
     super(msg);
    }
}
