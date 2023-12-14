package com.geektrust.backend.utils;

import com.geektrust.backend.entities.Driver;
import com.geektrust.backend.entities.Location;
import com.geektrust.backend.entities.Ride;
import com.geektrust.backend.entities.Rider;
import com.geektrust.backend.enums.RideStatus;
import com.geektrust.backend.exceptions.IncompleteRideException;
import com.geektrust.backend.exceptions.RideNotValidException;
import com.geektrust.backend.globalConstants.Constants;
import com.geektrust.backend.repositories.IRideRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RideFareUtilsTest {

    @Mock
    private IRideRepository rideRepository;

    private RideFareUtils rideFareUtils;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        rideFareUtils = new RideFareUtils(rideRepository);
    }

    @Test
    void testCalculateBill_ValidRide() {
        // Setup
        String rideId = "RIDE001";
        Ride mockRide = createMockRide();
        when(rideRepository.getRideById(rideId)).thenReturn(Optional.of(mockRide));
    
        // Execution
        double bill = rideFareUtils.calculateBill(rideId);
    
        // Expected bill calculation
        double distance = GeoLocationUtils.calculateDistance(mockRide.getSourceLocation(), mockRide.getDestination()); // Correct calculation of distance
        double distanceFare = distance * Constants.ADDITIONAL_PER_KM_FARE;
        double timeFare = mockRide.getElapsedTime() * Constants.ADDITIONAL_PER_MINUTE_FARE; // Use elapsed time from mock ride
        double totalFareBeforeTax = Constants.BASE_FARE + distanceFare + timeFare;
        double serviceTax = totalFareBeforeTax * Constants.SERVICE_TAX;
        double expectedBill = totalFareBeforeTax + serviceTax;
    
        // Verification
        assertEquals(expectedBill, bill, 0.01); // Allow a small delta for floating point comparisons
    }
    
    // Additional test cases...

    private Ride createMockRide() {
        // Create dummy locations for start and destination
        Location startLocation = new Location(2, 2); // Example start location
        Location endLocation = new Location(4, 5); // Example end location, forms a 3-4-5 right triangle

        // Create dummy start and end times
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.now().plusHours(1); // 1 hour later

        // Create dummy rider and driver
        Rider rider = new Rider.Builder().setId("RIDER001").build();
        Driver driver = new Driver.Builder().setId("DRIVER001").build();

        // Calculate dummy elapsed time in minutes
        int elapsedTime = 32; // 1 hour

        // Build and return the Ride object
        return new Ride.Builder()
                .setId("RIDE001")
                .setPassenger(rider)
                .setDriver(driver)
                .setSourceLocation(startLocation)
                .setDestination(endLocation)
                .setStartTime(startTime)
                .setEndTime(endTime)
                .setTotalBill(186.72) // Total bill will be calculated by the test
                .setElapsedTime(elapsedTime)
                .setRideStatus(RideStatus.COMPLETED)
                .build();
    }

    @Test
void testCalculateBill_IncompleteRide() {
    String rideId = "RIDE002";
    Ride mockRide = createMockRideWithNoEndTime(rideId); // Implement a method to create a mock Ride with no end time
    when(rideRepository.getRideById(rideId)).thenReturn(Optional.of(mockRide));
    
    assertThrows(IncompleteRideException.class, () -> rideFareUtils.calculateBill(rideId));
}


@Test
void testCalculateBill_InvalidRide() {
    String invalidRideId = "INVALID_RIDE_ID";
    when(rideRepository.getRideById(invalidRideId)).thenReturn(Optional.empty());
    
    assertThrows(RideNotValidException.class, () -> rideFareUtils.calculateBill(invalidRideId));
}


private Ride createMockRideWithNoEndTime(String rideId) {
    // Create dummy locations for start and destination
    Location startLocation = new Location(2, 2); // Example start location
    Location endLocation = new Location(4, 5); // Example end location, forms a 3-4-5 right triangle

    // Create dummy start time
    LocalDateTime startTime = LocalDateTime.now();

    // Create dummy rider and driver
    Rider rider = new Rider.Builder().setId("RIDER001").build();
    Driver driver = new Driver.Builder().setId("DRIVER001").build();

    // Calculate dummy elapsed time in minutes
    // Note: For an incomplete ride, you might choose not to set elapsed time or set it to a default value
    int elapsedTime = 0; // Assuming no elapsed time for an incomplete ride

    // Build and return the Ride object without setting an endTime
    return new Ride.Builder()
            .setId(rideId)
            .setPassenger(rider)
            .setDriver(driver)
            .setSourceLocation(startLocation)
            .setDestination(endLocation)
            .setStartTime(startTime)
            .setTotalBill(0) // Assuming no bill calculated yet for an incomplete ride
            .setElapsedTime(elapsedTime)
            .setRideStatus(RideStatus.STARTED) // Assuming the ride status is STARTED
            .build();
}


}

