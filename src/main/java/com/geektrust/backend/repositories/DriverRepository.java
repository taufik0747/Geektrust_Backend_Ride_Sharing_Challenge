package com.geektrust.backend.repositories;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import com.geektrust.backend.entities.Driver;
import com.geektrust.backend.entities.Location;
import com.geektrust.backend.enums.AvailabilityStatus;
import com.geektrust.backend.exceptions.DriverAlreadyExistsException;
import com.geektrust.backend.globalConstants.Constants;
import com.geektrust.backend.utils.GeoLocationUtils;

public class DriverRepository implements IDriverRepository {
    private Map<String, Driver> drivers;

    public DriverRepository() {
        drivers = new HashMap<>();
    }

    @Override
    public List<Driver> findAll() {
        return new ArrayList<>(drivers.values());
    }

    @Override
    public Optional<Driver> findDriverById(String driverId) {
        return Optional.ofNullable(drivers.get(driverId));
    }

    @Override
    public void addDriver(String driverId, Location yourLocation) {
        if(drivers.containsKey(driverId)) {
            throw new DriverAlreadyExistsException();
        }

        Driver driver = new Driver.Builder().setId(driverId).setYourLocation(yourLocation).setAvailabilityStatus(AvailabilityStatus.AVAILABLE).build();

        drivers.put(driverId, driver);
    }

    public List<Driver> getAvailableDriversWithin5Kms(Location yourLocation) {
        return drivers.values().stream().filter(driver -> driver.getAvailabilityStatus() == AvailabilityStatus.AVAILABLE).filter(driver -> GeoLocationUtils.calculateDistance(driver.getYourLocation(), yourLocation) <= Constants.DISTANCE_RADIUS_LIMIT).sorted(Comparator.comparingDouble(driver -> GeoLocationUtils.calculateDistance(driver.getYourLocation(), yourLocation))).collect(Collectors.toList());
    }

    @Override
    public void updateDriver(Driver driver) {
        drivers.put(driver.getId(), driver);
    }

    
}
