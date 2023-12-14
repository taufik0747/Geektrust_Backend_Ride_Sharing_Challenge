package com.geektrust.backend.services;

import java.util.List;
import java.util.Optional;
import com.geektrust.backend.entities.Driver;
import com.geektrust.backend.entities.Location;
import com.geektrust.backend.exceptions.DriverAlreadyExistsException;
import com.geektrust.backend.repositories.IDriverRepository;

/**
 * Service class for managing drivers.
 */
public class DriverService implements IDriverService {
    private final IDriverRepository driverRepository;

    /**
     * Constructs a new DriverService with the given driver repository.
     *
     * @param driverRepository the driver repository
     */
    public DriverService(IDriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    /**
     * Adds a new driver.
     *
     * @param driverId the ID of the driver
     * @param yourLocation the location of the driver
     * @throws DriverAlreadyExistsException if a driver with the same ID already exists
     */
    @Override
    public void addDriver(String driverId, Location yourLocation) throws DriverAlreadyExistsException {
        Optional<Driver> existingDriver = driverRepository.findDriverById(driverId);
        if (existingDriver.isPresent()) {
            throw new DriverAlreadyExistsException("Driver with ID " + driverId + " already exists");
        }
        driverRepository.addDriver(driverId, yourLocation);
    }

    /**
     * Returns a list of all drivers.
     *
     * @return a list of all drivers
     */
    @Override
    public List<Driver> getAllDrivers() {
        return driverRepository.findAll();
    }

     /**
     * Returns the driver with the given ID, if it exists.
     *
     * @param driverId the ID of the driver
     * @return an Optional containing the driver if it exists, or an empty Optional if it does not
     */
    @Override
    public Optional<Driver> getDriverById(String driverId) {
        return driverRepository.findDriverById(driverId);
    }
    
}
