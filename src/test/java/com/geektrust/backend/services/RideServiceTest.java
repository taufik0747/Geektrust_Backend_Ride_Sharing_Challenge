package com.geektrust.backend.services;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import com.geektrust.backend.entities.Driver;
import com.geektrust.backend.entities.Location;
import com.geektrust.backend.entities.Ride;
import com.geektrust.backend.entities.Rider;
import com.geektrust.backend.enums.AvailabilityStatus;
import com.geektrust.backend.enums.RideStatus;
import com.geektrust.backend.exceptions.IncompleteRideException;
import com.geektrust.backend.exceptions.NoDriversAvailableException;
import com.geektrust.backend.repositories.IDriverRepository;
import com.geektrust.backend.repositories.IRideRepository;
import com.geektrust.backend.repositories.IRiderRepository;
import com.geektrust.backend.utils.RideFareUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RideServiceTest {

    @Captor
    private ArgumentCaptor<Ride> rideCaptor;

    @InjectMocks
    private RideService rideService;

    @Mock
    private RideFareUtils rideFareUtils;

    @Mock
    private IDriverRepository driverRepository;

    @Mock
    private IRideRepository rideRepository;

    @Mock
    private IRiderRepository riderRepository;

    @Test
    public void testMatchRider() throws NoDriversAvailableException, IncompleteRideException {
        // given
        String passengerId = "R01";
        Location location = new Location(100, 100);
        Rider passenger = new Rider.Builder()
        .setId(passengerId)
        .setYourLocation(location)
        .build();

        List<Driver> drivers = Arrays.asList(new Driver.Builder().setId("D01")
                                .setYourLocation(new Location(100, 90))
                                 .setAvailabilityStatus(AvailabilityStatus.AVAILABLE).build());

        when(riderRepository.getRiderById(passengerId)).thenReturn(Optional.of(passenger));
        when(driverRepository.getAvailableDriversWithin5Kms(location)).thenReturn(drivers);
        // when
        List<Driver> matchedDrivers = rideService.matchRider(passengerId);
        // then
        assertEquals(drivers, matchedDrivers);
    }

    @Test
    void testGenerateBill() throws Exception {
        // Setup
        String rideId = "RIDE001";
        double expectedBill = 150.0;

        when(rideFareUtils.calculateBill(rideId)).thenReturn(expectedBill);

        // Execution
        double actualBill = rideService.generateBill(rideId);

        // Verification
        assertEquals(expectedBill, actualBill, "The calculated bill should match the expected bill.");
    }

    @Test
    void testStopRide_Success() {
        // Setup
        String rideId = "RIDE001"; // Define the rideId
        double destinationXCoordinate = 10.0;
        double destinationYCoordinate = 15.0;
        int elapsedTime = 30;

        Driver driver = new Driver.Builder().setId("DRIVER001").build();
        Location startLocation = new Location(5, 5);
        Ride ongoingRide = new Ride.Builder()
            .setId(rideId)
            .setDriver(driver)
            .setSourceLocation(startLocation)
            .setStartTime(LocalDateTime.now().minusMinutes(elapsedTime))
            .setRideStatus(RideStatus.STARTED)
            .build();

        when(rideRepository.getDriverByRideId(rideId)).thenReturn(driver);
        when(rideRepository.getRideById(rideId)).thenReturn(Optional.of(ongoingRide));

        // Execution
        String result = rideService.stopRide(rideId, destinationXCoordinate, destinationYCoordinate, elapsedTime);

        // Capture the ride object passed to updateRide
        verify(rideRepository).updateRide(rideCaptor.capture());
        Ride updatedRide = rideCaptor.getValue();

        // Verification
        assertEquals("RIDE_STOPPED " + rideId, result);
        assertEquals(RideStatus.COMPLETED, updatedRide.getRideStatus());
        assertEquals(elapsedTime, updatedRide.getElapsedTime());
        assertEquals(destinationXCoordinate, updatedRide.getDestination().getXCoordinate());
        assertEquals(destinationYCoordinate, updatedRide.getDestination().getYCoordinate());
        // Additional verifications as needed
    }
    
    
}

