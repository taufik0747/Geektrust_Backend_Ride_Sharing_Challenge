package com.geektrust.backend.entities;

import java.time.LocalDateTime;
import com.geektrust.backend.enums.RideStatus;

public class Ride extends BaseEntity {
    private final Rider passenger;
    private final Driver driver;
    private final Location sourceLocation;
    private final Location destination;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final double totalBill;
    private final int elapsedTime;
    private final RideStatus rideStatus;

    public Ride(Builder builder) {
        super(builder.id);
        this.passenger = builder.passenger;
        this.driver = builder.driver;
        this.sourceLocation = builder.sourceLocation;
        this.destination = builder.destination;
        this.startTime = builder.startTime;
        this.endTime = builder.endTime;
        this.totalBill = builder.totalBill;
        this.elapsedTime = builder.elapsedTime;
        this.rideStatus = builder.rideStatus;
    }

    public Rider getPassenger() {
        return passenger;
    }

    public Driver getDriver() {
        return driver;
    }

    public Location getSourceLocation() {
        return sourceLocation;
    }

    public Location getDestination() {
        return destination;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }
    
    public LocalDateTime getEndTime() {
        return endTime;
    }

    public double getTotalBill() {
        return totalBill;
    }

    public int getElapsedTime() {
        return elapsedTime;
    }

    public RideStatus getRideStatus() {
        return rideStatus;
    }

    public static class Builder {
        private String id;
        private Rider passenger;
        private Driver driver;
        private Location sourceLocation;
        private Location destination;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private double totalBill;
        private int elapsedTime;
        private RideStatus rideStatus;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setPassenger(Rider passenger) {
            this.passenger = passenger;
            return this;
        }

        public Builder setDriver(Driver driver) {
            this.driver = driver;
            return this;
        }

        public Builder setSourceLocation(Location sourceLocation) {
            this.sourceLocation = sourceLocation;
            return this;
        }

        public Builder setDestination(Location destination) {
            this.destination = destination;
            return this;
        }

        public Builder setStartTime(LocalDateTime startTime) {
            this.startTime = startTime;
            return this;
        }

        public Builder setEndTime(LocalDateTime endTime) {
            this.endTime = endTime;
            return this;
        }

        public Builder setTotalBill(double totalBill) {
            this.totalBill = totalBill;
            return this;
        }

        public Builder setElapsedTime(int elapsedTime) {
            this.elapsedTime = elapsedTime;
            return this;
        }

        public Builder setRideStatus(RideStatus rideStatus) {
            this.rideStatus = rideStatus;
            return this;
        }

        public Ride build() {
            return new Ride(this);
        }
    }
}
