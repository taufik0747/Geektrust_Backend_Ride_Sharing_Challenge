package com.geektrust.backend.repositories;

import java.util.List;
import java.util.Optional;
import com.geektrust.backend.entities.Driver;
import com.geektrust.backend.entities.Ride;

/**
 * Interface for repositories that manage rides.
 */
public interface IRideRepository {
   
    /**
     * Adds a new ride.
     *
     * @param ride The ride to add.
     */
    void addRide(Ride ride);

    /**
     * Finds all rides for a given driver.
     *
     * @param driverId The ID of the driver.
     * @return A list of all rides for the given driver.
     */
    List<Ride> getRidesForDriver(String driverId);

    /**
     * Finds all rides for a given rider.
     *
     * @param passengerId The ID of the rider(passenger).
     * @return A list of all rides for the given rider.
     */
    List<Ride> getRidesForRider(String passengerId);

     /**
     * Finds a ride by ID.
     *
     * @param rideId The ID of the ride to find.
     * @return An Optional containing the found ride, or empty if no ride was found with the given ID.
     */
    Optional<Ride> getRideById(String rideId);

    /**
     * Finds all rides.
     *
     * @return A list of all rides.
     */
    List<Ride> getAllRides();

     /**
     * Updates a ride.
     *
     * @param ride The ride to update.
     */
    void updateRide(Ride ride);

    /**
     * Finds the driver for a given ride.
     *
     * @param rideId The ID of the ride.
     * @return The driver for the given ride.
     */
    Driver getDriverByRideId(String rideId);
}
