package com.geektrust.backend.services;

import java.util.List;
import java.util.Optional;
import com.geektrust.backend.entities.Driver;
import com.geektrust.backend.entities.Location;
import com.geektrust.backend.exceptions.DriverAlreadyExistsException;

/**
 * Service interface for managing drivers.
 */
public interface IDriverService {
    
     /**
     * Adds a new driver.
     *
     * @param driverId the ID of the driver
     * @param yourLocation the location of the driver
     * @throws DriverAlreadyExistsException if a driver with the same ID already exists
     */
    void addDriver(String driverId, Location yourLocation) throws DriverAlreadyExistsException;

    /**
     * Returns a list of all drivers.
     *
     * @return a list of all drivers
     */
    List<Driver> getAllDrivers();
    
    /**
     * Returns the driver with the given ID, if it exists.
     *
     * @param driverId the ID of the driver
     * @return an Optional containing the driver if it exists, or an empty Optional if it does not
     */
    Optional<Driver> getDriverById(String driverId);

}
