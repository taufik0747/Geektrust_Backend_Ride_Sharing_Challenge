package com.geektrust.backend.entities;

public class Location {
    private final double xCoordinate;
    private final double yCoordinate;

    public Location(double xCoordinate, double yCoordinate){
        this.xCoordinate=xCoordinate;
        this.yCoordinate=yCoordinate;
    }

    public double getXCoordinate(){
        return xCoordinate;
    }
    
    public double getYCoordinate(){
        return yCoordinate;
    }
}
