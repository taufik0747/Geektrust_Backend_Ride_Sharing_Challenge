package com.geektrust.backend.repositories;

import java.util.List;
import java.util.Optional;
import com.geektrust.backend.entities.Driver;
import com.geektrust.backend.entities.Location;

/**
 * Interface for repositories that manage drivers.
 */
public interface IDriverRepository {
   
    /**
     * Finds all drivers.
     *
     * @return A list of all drivers.
     */
    List<Driver> findAll();
    
    /**
     * Finds a driver by ID.
     *
     * @param id The ID of the driver to find.
     * @return An Optional containing the found driver, or empty if no driver was found with the given ID.
     */
    Optional<Driver> findDriverById(String id);

    /**
     * Adds a new driver.
     *
     * @param driverId The ID of the new driver.
     * @param yourLocation The location of the new driver.
     */
    void addDriver(String driverId, Location yourLocation);

    /**
     * Finds all drivers within 5 kms of a given location.
     *
     * @param yourLocation The location to find drivers near.
     * @return A list of all drivers within 5 kms of the given location.
     */
    List<Driver> getAvailableDriversWithin5Kms(Location yourLocation);

    /**
     * Updates a driver.
     *
     * @param driver The driver to update.
     */
    void updateDriver(Driver driver);
}
