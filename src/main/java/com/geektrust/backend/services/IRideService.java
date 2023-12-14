package com.geektrust.backend.services;

import java.util.List;
import java.util.Optional;
import com.geektrust.backend.entities.Driver;
import com.geektrust.backend.entities.Ride;

/**
 * Service interface for managing ride sharing operations.
 */
public interface IRideService {
    
    /**
     * Matches a rider with available drivers.
     *
     * @param passengerId the ID of the rider(passenger)
     * @return a list of matched drivers
     */
    List<Driver> matchRider(String passengerId);

    /**
     * Starts a ride for a rider with a specific driver.
     *
     * @param rideId the ID of the ride
     * @param driverId the ID of the driver
     * @param passengerId the ID of the rider(passenger)
     * @return an Optional containing the started ride if it exists, or an empty Optional if it does not
     */
    Optional<Ride> startRide(String rideId, String driverId, String passengerId);

     /**
     * Stops a ride.
     *
     * @param rideId the ID of the ride
     * @param destinationXCoordinate the X-coordinate of the destination
     * @param destinationYCoordinate the Y-coordinate of the destination
     * @param elapsedTime the time taken for the ride
     * @return a string representing the output of the operation
     */
    String stopRide(String rideId, double destinationXCoordinate, double destinationYCoordinate, int elapsedTime);

    /**
     * Generates the bill for a ride.
     *
     * @param rideId the ID of the ride
     * @return the bill for the ride
     */
    double generateBill(String rideId);

    /**
     * Returns the ride with the given ID, if it exists.
     *
     * @param rideId the ID of the ride
     * @return an Optional containing the ride if it exists, or an empty Optional if it does not
     */
    Optional<Ride> getRideById(String rideId);

     /**
     * Returns the driver with the given ID, if it exists.
     *
     * @param driverId the ID of the driver
     * @return an Optional containing the driver if it exists, or an empty Optional if it does not
     */
    Optional<Driver> getDriverById(String driverId);
}
