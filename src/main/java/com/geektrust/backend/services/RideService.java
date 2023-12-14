package com.geektrust.backend.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import com.geektrust.backend.entities.Driver;
import com.geektrust.backend.entities.Location;
import com.geektrust.backend.entities.Ride;
import com.geektrust.backend.entities.Rider;
import com.geektrust.backend.enums.AvailabilityStatus;
import com.geektrust.backend.enums.RideStatus;
import com.geektrust.backend.exceptions.IncompleteRideException;
import com.geektrust.backend.exceptions.NoDriversAvailableException;
import com.geektrust.backend.exceptions.RideNotValidException;
import com.geektrust.backend.globalConstants.Constants;
import com.geektrust.backend.repositories.IDriverRepository;
import com.geektrust.backend.repositories.IRideRepository;
import com.geektrust.backend.repositories.IRiderRepository;
import com.geektrust.backend.utils.GeoLocationUtils;
import com.geektrust.backend.utils.RideFareUtils;

public class RideService implements IRideService {
    
    private final RideFareUtils rideFareUtils;

    private final IDriverRepository driverRepository;
    private final IRideRepository rideRepository;
    private final IRiderRepository riderRepository;

    public RideService(IDriverRepository driverRepository, IRideRepository rideRepository, IRiderRepository riderRepository, RideFareUtils rideFareUtils) {
        this.driverRepository = driverRepository;
        this.rideRepository = rideRepository;
        this.riderRepository = riderRepository;
        this.rideFareUtils = rideFareUtils; 
    }

    @Override
    public List<Driver> matchRider(String passengerId) {

        Optional<Rider> optionalRider = riderRepository.getRiderById(passengerId);
        Rider passenger = optionalRider.orElseThrow(() -> new IncompleteRideException(Constants.INVALID_RIDE_MESSAGE));

        if(passenger.getCurrentRide() != null) {
            throw new IncompleteRideException(Constants.INCOMPLETE_RIDE_MESSAGE);
        }

        Location passengerLocation = passenger.getYourLocation();
        List<Driver> driversAvailable = driverRepository.getAvailableDriversWithin5Kms(passengerLocation);

        if(driversAvailable.isEmpty()) {
            throw new NoDriversAvailableException(Constants.NO_DRIVERS_AVAILABLE_MESSAGE);
        }

        Map<Double, List<Driver>> driversSortedByDistance = new TreeMap<>();
        for(Driver driver : driversAvailable) {
            double distance = GeoLocationUtils.calculateDistance(passengerLocation, driver.getYourLocation());
            driversSortedByDistance.computeIfAbsent(distance, k -> new ArrayList<>()).add(driver);
        }

        List<Driver> correspondingDrivers = new ArrayList<>();
        for(List<Driver> drivers : driversSortedByDistance.values()) {
            Collections.sort(drivers, Comparator.comparing(Driver::getId));
            correspondingDrivers.addAll(drivers);
            if(correspondingDrivers.size() >= Constants.MAX_DRIVERS) {
                break;
            }
        }

        correspondingDrivers.forEach(driverRepository::updateDriver);
        return correspondingDrivers.subList(0, Math.min(correspondingDrivers.size(), Constants.MAX_DRIVERS));
    }

    @Override
    public Optional<Ride> startRide(String rideId, String driverId, String passengerId) throws RideNotValidException, NoDriversAvailableException {

        Rider passenger = validateRiderDetails(passengerId);
        Driver driver = validateDriverDetails(passenger, driverId);

        Location sourceLocation = new Location(passenger.getYourLocation().getXCoordinate(), passenger.getYourLocation().getYCoordinate());
        LocalDateTime startTime = LocalDateTime.now();

        Ride ride = new Ride.Builder().setId(rideId).setPassenger(passenger).setDriver(driver).setSourceLocation(sourceLocation).setRideStatus(RideStatus.STARTED).setStartTime(startTime).build();
        riderRepository.setPresentRideId(passengerId, rideId);
        rideRepository.addRide(ride);

        Driver updatedDriverDetails = new Driver.Builder().setId(driver.getId()).setYourLocation(driver.getYourLocation()).setAvailabilityStatus(AvailabilityStatus.NOT_AVAILABLE).build();
        driverRepository.updateDriver(updatedDriverDetails);
        return Optional.of(ride);
    
    }

    @Override
    public String stopRide(String rideId, double destinationXCoordinate, double destinationYCoordinate, int elapsedTime) {
        if(elapsedTime < Constants.INPUT_FILE_ARG_INDEX) {
            throw new IllegalArgumentException("Time taken for a ride cannot be negative");
        }

        Driver driver = rideRepository.getDriverByRideId(rideId);
        Optional<Ride> optionalRide = rideRepository.getRideById(rideId);
        if(optionalRide.isPresent()) {
            Ride ride = optionalRide.get();
            if(ride.getRideStatus() == RideStatus.STARTED) {
                Location destination = new Location(destinationXCoordinate, destinationYCoordinate);

                Driver updatedDriverDetails = new Driver.Builder().setId(driver.getId()).setYourLocation(driver.getYourLocation()).setAvailabilityStatus(AvailabilityStatus.AVAILABLE).build();

                driverRepository.updateDriver(updatedDriverDetails);

                ride = new Ride.Builder().setId(rideId).setDriver(driver).setSourceLocation(ride.getSourceLocation()).setDestination(destination).setEndTime(LocalDateTime.now()).setElapsedTime(elapsedTime).setRideStatus(RideStatus.COMPLETED).build();

                rideRepository.updateRide(ride);

                return "RIDE_STOPPED " + ride.getId();
            }
            else {
                throw new RideNotValidException(Constants.INVALID_RIDE_MESSAGE);
            }
        }   else {
                throw new RideNotValidException(Constants.INVALID_RIDE_MESSAGE);
            }
        }

        private Rider validateRiderDetails(String passengerId) throws RideNotValidException {

            Optional<Rider> optionalRider = riderRepository.getRiderById(passengerId);
            if(!optionalRider.isPresent()) {
                throw new RideNotValidException(Constants.INVALID_RIDER_ID_MESSAGE);
            }
            Rider passenger = optionalRider.get();
            if(passenger.getCurrentRide() != null) {
                throw new RideNotValidException(Constants.INCOMPLETE_RIDE_MESSAGE);
            }
            return passenger;
        }

        private Driver validateDriverDetails(Rider passenger, String driverId) throws RideNotValidException {
            List<Driver> correspondingDrivers = passenger.getCorrespondingDriverIds();
            if(correspondingDrivers == null || correspondingDrivers.isEmpty()) {
                throw new RideNotValidException(Constants.INVALID_RIDE_MESSAGE);
            }
            int index = Integer.parseInt(driverId) - Constants.INDEX;
            if(index < 0 || index >= correspondingDrivers.size()) {
                throw new RideNotValidException("INVALID_DRIVER_INDEX");
            }

            Driver assignedDriver = correspondingDrivers.get(index);
            Optional<Driver> optionalDriver = driverRepository.findDriverById(assignedDriver.getId());
            if(!optionalDriver.isPresent()) {
                throw new RideNotValidException(Constants.INVALID_RIDE_MESSAGE);
            }
            return optionalDriver.get();
        }

        @Override
        public double generateBill(String rideId) {
            double totalTripCharge = rideFareUtils.calculateBill(rideId);
            return totalTripCharge;
        }

        @Override
        public Optional<Ride> getRideById(String rideId) {
            return rideRepository.getRideById(rideId);
        }

        @Override
        public Optional<Driver> getDriverById(String driverId) {
            return driverRepository.findDriverById(driverId);
        }
    }

    
    
