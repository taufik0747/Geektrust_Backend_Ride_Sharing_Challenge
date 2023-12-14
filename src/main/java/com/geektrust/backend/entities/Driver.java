package com.geektrust.backend.entities;

import com.geektrust.backend.enums.AvailabilityStatus;

public class Driver extends BaseEntity {
    private final Location yourLocation;
    private final AvailabilityStatus availabilityStatus;

    private Driver(Builder builder) {
        super(builder.id);
        this.yourLocation= builder.yourLocation;
        this.availabilityStatus= builder.availabilityStatus;
    }
    
    public Location getYourLocation() {
        return yourLocation;
    }

    public AvailabilityStatus getAvailabilityStatus() {
        return availabilityStatus;
    }

    public boolean isAvailable() {
        return availabilityStatus == AvailabilityStatus.AVAILABLE;
    }

    public static class Builder {
        private String id;
        private Location yourLocation;
        private AvailabilityStatus availabilityStatus = AvailabilityStatus.AVAILABLE;

        public Builder setId(String id) {
            this.id = id;
            return this;
        }

        public Builder setYourLocation(Location yourLocation) {
            this.yourLocation = yourLocation;
            return this;
        }

        public Builder setAvailabilityStatus(AvailabilityStatus availabilityStatus) {
            this.availabilityStatus = availabilityStatus;
            return this;
        }

        public Driver build() {
            return new Driver(this);
        }
    }
    
}
