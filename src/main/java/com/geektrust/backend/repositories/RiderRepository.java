package com.geektrust.backend.repositories;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.geektrust.backend.entities.Driver;
import com.geektrust.backend.entities.Rider;
import com.geektrust.backend.exceptions.RideNotValidException;
import com.geektrust.backend.globalConstants.Constants;

public class RiderRepository implements IRiderRepository {
    private final Map<String, Rider> riderMap;

    public RiderRepository() {
        riderMap = new HashMap<>();
    }

    @Override
    public void addRider(Rider passenger) {
        riderMap.put(passenger.getId(), passenger);
    }

    @Override
    public Optional<Rider> getRiderById(String passengerId) {
        return Optional.ofNullable(riderMap.get(passengerId));
    }

    @Override
    public void updateRider(String id, Rider passenger) {
        riderMap.put(id, passenger);
    }

    @Override
    public void deleteRider(String id) {
        riderMap.remove(id);
    }

    @Override
    public void setPresentRideId(String passengerId, String rideId) {
        Rider passenger = riderMap.get(passengerId);
        if(passenger != null) {
            passenger = new Rider.Builder().setId(passengerId).setPresentRideId(rideId).build();
            riderMap.put(passengerId, passenger);
        }
    }

    @Override
    public void setCorrespondingDrivers(String passengerId, List<Driver> correspondingDrivers) throws RideNotValidException {
        Optional<Rider> optionalRider = getRiderById(passengerId);

        if(!optionalRider.isPresent()) {
            throw new RideNotValidException(Constants.INVALID_RIDER_ID_MESSAGE);
        }

        Rider passenger = optionalRider.get();

        Rider updatedRiderDetails = new Rider.Builder().setId(passengerId).setYourLocation(passenger.getYourLocation()).setCurrentRide(passenger.getCurrentRide()).setPresentRideId(passenger.getPresentRideId()).setCorrespondingDriverIds(correspondingDrivers).build();

        updateRider(updatedRiderDetails.getId(), updatedRiderDetails);
    }
    
}
