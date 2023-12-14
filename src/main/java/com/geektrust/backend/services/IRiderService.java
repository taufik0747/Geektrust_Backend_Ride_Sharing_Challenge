package com.geektrust.backend.services;

import java.util.List;
import java.util.Optional;
import com.geektrust.backend.entities.Driver;
import com.geektrust.backend.entities.Rider;
import com.geektrust.backend.exceptions.RideNotValidException;

/**
 * Service interface for managing riders.
 */
public interface IRiderService {
    /**
     * Adds a new rider's location.
     *
     * @param passengerId the ID of the rider(passenger) 
     * @param xCoordinate the X-coordinate of the rider's location
     * @param yCoordinate the Y-coordinate of the rider's location
     */
    void addRider(String passengerId, double xCoordinate, double yCoordinate);

     /**
     * Returns the rider with the given ID, if it exists.
     *
     * @param id the ID of the rider
     * @return an Optional containing the rider if it exists, or an empty Optional if it does not
     */
    Optional<Rider> getRiderById(String id);

     /**
     * Sets the matched drivers for a rider.
     *
     * @param passengerId the ID of the rider(passenger)
     * @param correspondingDrivers the list of matched drivers
     * @throws RideNotValidException if the ride is invalid
     */
    void setCorrespondingDrivers(String passengerId, List<Driver> correspondingDrivers) throws RideNotValidException;
    
}
