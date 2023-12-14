package com.geektrust.backend.utils;

import java.util.Optional;
import com.geektrust.backend.entities.Location;
import com.geektrust.backend.entities.Ride;
import com.geektrust.backend.exceptions.IncompleteRideException;
import com.geektrust.backend.exceptions.RideNotValidException;
import com.geektrust.backend.globalConstants.Constants;
import com.geektrust.backend.repositories.IRideRepository;

public class RideFareUtils {
    
    private final IRideRepository rideRepository;

    public RideFareUtils(IRideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }

    public double calculateBill(String rideId) {
        Optional<Ride> ride = rideRepository.getRideById(rideId);
        if (!ride.isPresent()) {
            throw new RideNotValidException(Constants.INVALID_RIDE_MESSAGE);
        }

        Ride currentRide = ride.get();
        if (currentRide.getEndTime() == null) {
            throw new IncompleteRideException(Constants.INCOMPLETE_RIDE_MESSAGE);
        }

        double distance = calculateDistance(currentRide.getDestination(), currentRide.getSourceLocation());
        double fareByDistance = calculateFareByDistance(distance);
        double fareByTime = calculateFareByTime(currentRide.getElapsedTime());
        double totalFare = calculateTotalFare(fareByDistance, fareByTime);
        double serviceTax = calculateServiceTax(totalFare);
        double finalCost = calculateFinalCost(totalFare, serviceTax);

        return finalCost;
    }

    private double calculateDistance(Location destination, Location sourceLocation) {
        return GeoLocationUtils.calculateDistance(destination, sourceLocation);
    }

    private double calculateFareByDistance(double distance) {
        return round(distance * Constants.ADDITIONAL_PER_KM_FARE);
    }

    private double calculateFareByTime(int timeTaken) {
        return round(timeTaken * Constants.ADDITIONAL_PER_MINUTE_FARE);
    }

    private double calculateTotalFare(double distanceFare, double timeFare) {
        return round(Constants.BASE_FARE + distanceFare + timeFare);
    }

    private double calculateServiceTax(double totalFare) {
        return round(totalFare * Constants.SERVICE_TAX);
    }

    private double calculateFinalCost(double totalFare, double serviceTax) {
        return round(totalFare + serviceTax);
    }

    private double round(double value) {
        return Math.round(value * Constants.Two_Decimal_Place) / Constants.Two_Decimal_Place;
    }
}
