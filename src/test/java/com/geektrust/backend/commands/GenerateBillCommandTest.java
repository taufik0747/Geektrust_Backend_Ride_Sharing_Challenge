package com.geektrust.backend.commands;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import com.geektrust.backend.entities.Driver;
import com.geektrust.backend.entities.Location;
import com.geektrust.backend.entities.Ride;
import com.geektrust.backend.entities.Rider;
import com.geektrust.backend.enums.RideStatus;
import com.geektrust.backend.services.IRideService;
import java.time.LocalDateTime;
import java.util.Optional;


class GenerateBillCommandTest {

    @Mock
    private IRideService rideService;
    private GenerateBillCommand generateBillCommand;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        generateBillCommand = new GenerateBillCommand(rideService);
        System.setOut(new PrintStream(outContent)); // Capture System.out
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut); // Reset System.out
    }


    @Test
    void testExecute_ValidBillGeneration() {
        // Setup
        String rideId = "RIDE-001";
        double bill = 100.0;
        Ride mockRide = createMockRide(rideId); // Implement this method to create a mock Ride

        when(rideService.generateBill(rideId)).thenReturn(bill);
        when(rideService.getRideById(rideId)).thenReturn(Optional.of(mockRide));

        // Execute
        generateBillCommand.execute(Arrays.asList("GENERATE_BILL", rideId));

        // Verify
        verify(rideService).generateBill(rideId);
        verify(rideService).getRideById(rideId);
        assertTrue(outContent.toString().contains(String.format("BILL %s %s %.2f", rideId, mockRide.getDriver().getId(), bill)));

        // Cleanup
        System.setOut(System.out);
    }

    // Additional test cases...

    private Ride createMockRide(String rideId) {
        // Create mock instances for Rider, Driver, and other required fields
        Rider mockRider = new Rider.Builder().setId("RIDER123").build();
        Driver mockDriver = new Driver.Builder().setId("DRIVER123").build();
        Location mockSourceLocation = new Location(2, 2); // Example location
        Location mockDestination = new Location(4, 5);  // Example destination

        // Create and return a Ride instance using the builder pattern
        return new Ride.Builder()
            .setId(rideId)
            .setPassenger(mockRider)
            .setDriver(mockDriver)
            .setSourceLocation(mockSourceLocation)
            .setDestination(mockDestination)
            .setStartTime(LocalDateTime.now())
            .setEndTime(LocalDateTime.now().plusHours(1))
            .setTotalBill(186.72) // Example bill amount
            .setElapsedTime(32)  // Example elapsed time in minutes
            .setRideStatus(RideStatus.COMPLETED) // Example ride status
            .build();
    }

    

@Test
void testExecute_BillIsZero() {
    String rideId = "RIDE-002";
    when(rideService.generateBill(rideId)).thenReturn(0.0);
    when(rideService.getRideById(rideId)).thenReturn(Optional.of(createMockRide(rideId)));

    generateBillCommand.execute(Arrays.asList("GENERATE_BILL", rideId));

    // Expect no bill information printed as bill is zero
    assertFalse(outContent.toString().contains(String.format("BILL %s", rideId)));
    System.setOut(System.out); // Reset System.out
}


}
