package com.geektrust.backend.repositories;

import java.util.List;
import java.util.Optional;
import com.geektrust.backend.entities.Driver;
import com.geektrust.backend.entities.Rider;
import com.geektrust.backend.exceptions.RideNotValidException;

/**
 * Interface for repositories that manage riders.
 */
public interface IRiderRepository {
   
    /**
     * Adds a new rider.
     *
     * @param passenger The rider to add.
     */
    void addRider(Rider passenger);

    /**
     * Finds a rider(passenger) by ID.
     *
     * @param passengerId The ID of the rider to find.
     * @return An Optional containing the found rider, or empty if no rider was found with the given ID.
     */
    Optional<Rider> getRiderById(String passengerId);

    /**
     * Updates a rider(passenger).
     *
     * @param id The ID of the rider to update.
     * @param passenger The updated rider(passenger).
     */
    void updateRider(String id, Rider passenger);

    /**
     * Deletes a rider.
     *
     * @param id The ID of the rider to delete.
     */
    void deleteRider(String id);

    /**
     * Sets the active ride ID for a rider(passenger).
     *
     * @param passengerId The ID of the rider.
     * @param rideId The ID of the present ride.
     */
    void setPresentRideId(String passengerId, String rideId);

    /**
     * Sets the matched drivers for a rider(passenger).
     *
     * @param passengerId The ID of the passenger.
     * @param correspondingDrivers The list of matched drivers.
     * @throws RideNotValidException If the ride is invalid.
     */
    void setCorrespondingDrivers(String passengerId, List<Driver> correspondingDrivers) throws RideNotValidException;
    
}
