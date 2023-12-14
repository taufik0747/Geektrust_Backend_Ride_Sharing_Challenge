package com.geektrust.backend.repositories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import com.geektrust.backend.entities.Driver;
import com.geektrust.backend.entities.Ride;

public class RideRepository implements IRideRepository {
   
    private final Map<String, Ride> rideMap;

    public RideRepository() {
        rideMap = new HashMap<>();
    }

    public Driver getDriverByRideId(String rideId) {
        Optional<Ride> optionalRide = rideMap.values().stream().filter(ride -> ride.getId().equals(rideId)).findFirst();
        if(optionalRide.isPresent()){
            return optionalRide.get().getDriver();
        } else{
            return null;
        }

    }

    @Override
    public void addRide(Ride ride) {
        rideMap.putIfAbsent(ride.getId(), ride);
    }

    @Override
    public List<Ride> getRidesForDriver(String driverId) {
        return rideMap.values().stream().filter(ride -> ride.getDriver().getId().equals(driverId)).collect(Collectors.toList());
    }
    
    @Override
    public List<Ride> getRidesForRider(String passengerId) {
        return rideMap.values().stream().filter(ride -> ride.getPassenger().getId().equals(passengerId)).collect(Collectors.toList());
    }

    @Override
    public Optional<Ride> getRideById(String rideId) {
        return Optional.ofNullable(rideMap.get(rideId));
    }

    @Override
    public List<Ride> getAllRides() {
        return new ArrayList<>(rideMap.values());
    }

    @Override
    public void updateRide(Ride ride) {
        rideMap.put(ride.getId(), ride);
    }
    
}
