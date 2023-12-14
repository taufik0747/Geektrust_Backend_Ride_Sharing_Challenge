package com.geektrust.backend.services;

import java.util.List;
import java.util.Optional;
import com.geektrust.backend.entities.Driver;
import com.geektrust.backend.entities.Location;
import com.geektrust.backend.entities.Rider;
import com.geektrust.backend.exceptions.RideNotValidException;
import com.geektrust.backend.repositories.IRiderRepository;

/**
 * Service class for managing riders.
 */

public class RiderService implements IRiderService {
    private final IRiderRepository riderRepository;

    /**
     * Constructs a new RiderService with the given rider repository.
     *
     * @param riderRepository the rider repository
     */
    public RiderService(IRiderRepository riderRepository) {
        this.riderRepository = riderRepository;
    }

     /**
     * Adds a new rider.
     *
     * @param passengerId the ID of the rider(passenger)
     * @param locationXCoordinate the X-coordinate of the rider's location
     * @param locationYCoordinate the Y-coordinate of the rider's location
     * @throws IllegalArgumentException if a rider with the same ID already exists
     */
    @Override
    public void addRider(String passengerId, double locationXCoordinate, double locationYCoordinate) {
        Optional<Rider> existingRider = riderRepository.getRiderById(passengerId);
        if (existingRider.isPresent()) {
            throw new IllegalArgumentException("Rider with ID " + passengerId + " already exists");
        } else {
            Location riderLocation = new Location(locationXCoordinate, locationYCoordinate);
            Rider newRider = new Rider.Builder()
            .setId(passengerId)
            .setYourLocation(riderLocation)
            .build();
            riderRepository.addRider(newRider);
        }
    }

     /**
     * Returns the rider with the given ID, if it exists.
     *
     * @param id the ID of the rider
     * @return an Optional containing the rider if it exists, or an empty Optional if it does not
     */
    @Override
    public Optional<Rider> getRiderById(String id) {
        return riderRepository.getRiderById(id);
    }

    /**
     * Sets the matched drivers for a rider.
     *
     * @param passengerId the ID of the rider
     * @param correspondingDrivers the list of matched drivers
     * @throws RideNotValidException if the ride is invalid
     */
    @Override
    public void setCorrespondingDrivers(String passengerId, List<Driver> correspondingDrivers) throws RideNotValidException {
        Optional<Rider> existingRider = riderRepository.getRiderById(passengerId);
        if (!existingRider.isPresent()) {
            throw new RideNotValidException("Rider with ID " + passengerId + " does not exist");
        }
        riderRepository.setCorrespondingDrivers(passengerId, correspondingDrivers);
    }
        
}
