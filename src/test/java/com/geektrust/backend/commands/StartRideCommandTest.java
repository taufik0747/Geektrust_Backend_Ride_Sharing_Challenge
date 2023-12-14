package com.geektrust.backend.commands;


import com.geektrust.backend.entities.Driver;
import com.geektrust.backend.entities.Ride;
import com.geektrust.backend.entities.Rider;
import com.geektrust.backend.exceptions.NoDriversAvailableException;
import com.geektrust.backend.exceptions.RideNotValidException;
import com.geektrust.backend.services.IRideService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;

class StartRideCommandTest {

    @Mock
    private IRideService rideService;
    @Mock
    private PrintStream output;

    private StartRideCommand startRideCommand;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        startRideCommand = new StartRideCommand(rideService, output);
    }

    @Test
    void testExecute_RideStartedSuccessfully() {
        // Setup
        String rideId = "RIDE-001";
        String driverId = "D1";
        String passengerId = "P1";
    
        Ride mockRide = new Ride.Builder()
            .setId(rideId)
            .setPassenger(new Rider.Builder().setId(passengerId).build())
            .setDriver(new Driver.Builder().setId(driverId).build())
            // Set other necessary fields with mock or dummy values
            .build();
    
        when(rideService.startRide(rideId, driverId, passengerId)).thenReturn(Optional.of(mockRide));
    
        // Provide a list of tokens that matches the command's expectations
        startRideCommand.execute(Arrays.asList("SOME_COMMAND", rideId, driverId, passengerId));
    
        // Verify
        verify(rideService).startRide(rideId, driverId, passengerId);
        verify(output).println("RIDE_STARTED " + rideId);
    }


    @Test
void testExecute_NoDriversAvailable() {
    // Setup
    String rideId = "RIDE-002";
    String driverId = "D2";
    String passengerId = "P2";
    
    when(rideService.startRide(rideId, driverId, passengerId))
        .thenThrow(new NoDriversAvailableException("No drivers available"));
    
    // Execute
    startRideCommand.execute(Arrays.asList("SOME_COMMAND", rideId, driverId, passengerId));
    
    // Verify
    verify(rideService).startRide(rideId, driverId, passengerId);
    verify(output).println("INVALID_RIDE");
}


@Test
void testExecute_InvalidRideRequest() {
    // Setup
    String rideId = "RIDE-003";
    String driverId = "D3";
    String passengerId = "P3";
    
    when(rideService.startRide(rideId, driverId, passengerId))
        .thenThrow(new RideNotValidException("Invalid ride request"));
    
    // Execute
    startRideCommand.execute(Arrays.asList("SOME_COMMAND", rideId, driverId, passengerId));
    
    // Verify
    verify(rideService).startRide(rideId, driverId, passengerId);
    verify(output).println("INVALID_RIDE");
}


}
