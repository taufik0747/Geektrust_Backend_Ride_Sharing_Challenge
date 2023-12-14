package com.geektrust.backend.entities;

import java.util.List;

public class Rider extends BaseEntity {
    private final Location yourLocation;
    private final Ride currentRide;
    private final String presentRideId;
    private final List<Driver> correspondingDriverIds;

    private Rider(Builder builder) {
        super(builder.passengerId);
        this.yourLocation = builder.yourLocation;
        this.currentRide = builder.currentRide;
        this.presentRideId = builder.presentRideId;
        this.correspondingDriverIds = builder.correspondingDriverIds;
    }

    public Location getYourLocation() {
        return yourLocation;
    }

    public Ride getCurrentRide() {
        return currentRide;
    }

    public String getPresentRideId() {
        return presentRideId;
    }

    public List<Driver> getCorrespondingDriverIds() {
        return correspondingDriverIds;
    }

    public static class Builder {
        private String passengerId;
        private Location yourLocation;
        private Ride currentRide;
        private String presentRideId;
        private List<Driver> correspondingDriverIds;

        public Builder setId(String passengerId) {
            this.passengerId = passengerId;
            return this;
        }

        public Builder setYourLocation(Location yourLocation) {
            this.yourLocation = yourLocation;
            return this;
        }

        public Builder setCurrentRide(Ride currentRide) {
            this.currentRide = currentRide;
            return this;
        }

        public Builder setPresentRideId(String presentRideId) {
            this.presentRideId = presentRideId;
            return this;
        }

        public Builder setCorrespondingDriverIds(List<Driver> correspondingDriverIds) {
            this.correspondingDriverIds = correspondingDriverIds;
            return this;
        }

        public Rider build() {
            return new Rider(this);
        }
    }

}
