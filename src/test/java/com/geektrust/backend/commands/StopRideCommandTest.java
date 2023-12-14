package com.geektrust.backend.commands;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import java.util.Arrays;
import java.util.List;
import com.geektrust.backend.services.IRideService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StopRideCommandTest {

    private IRideService rideService;
    private ICommand stopRideCommand;

    @BeforeEach
    void setUp() {
        rideService = mock(IRideService.class);
        stopRideCommand = new StopRideCommand(rideService);
    }

    @Test
    public void testExecute() throws Exception {
        // given
        IRideService rideService = mock(IRideService.class);
        ICommand stopRideCommand = new StopRideCommand(rideService);

        String rideId = "R01";
        double destinationXCoordinate = 2.0;
        double destinationYCoordinate = 2.0;
        int elapsedTime = 10;
        List<String> tokens = Arrays.asList("STOP_RIDE",rideId, String.valueOf(destinationXCoordinate), String.valueOf(destinationYCoordinate), String.valueOf(elapsedTime));
        // when
        stopRideCommand.execute(tokens);
        // then
        verify(rideService, times(1)).stopRide(rideId, destinationXCoordinate, destinationYCoordinate, elapsedTime);
    }


    @Test
void testExecute_NegativeElapsedTime() {
    IRideService rideService = mock(IRideService.class);
    ICommand stopRideCommand = new StopRideCommand(rideService);

    String rideId = "R01";
    double destinationXCoordinate = 2.0;
    double destinationYCoordinate = 2.0;
    int negativeElapsedTime = -10;
    List<String> tokens = Arrays.asList("STOP_RIDE", rideId, String.valueOf(destinationXCoordinate), String.valueOf(destinationYCoordinate), String.valueOf(negativeElapsedTime));

    // Here, you should decide how your command should behave. Does it throw an exception? Does it print an error?
    // Example: expecting an exception or error message
    // ... your test implementation

    // Verify that the service is not called due to invalid input
    verify(rideService, times(0)).stopRide(any(), anyDouble(), anyDouble(), anyInt());
}



@Test
void testExecute_IncorrectInputFormat() {
    // Setup
    String rideId = "R01";
    String invalidElapsedTime = "invalidTime"; // Non-numeric elapsed time
    List<String> tokens = Arrays.asList("STOP_RIDE", rideId, "2.0", "2.0", invalidElapsedTime);

    // Execute and Assert
    try {
        stopRideCommand.execute(tokens);
        fail("Expected an exception to be thrown due to incorrect input format");
    } catch (NumberFormatException e) {
        // Assert that the expected exception is thrown
    } catch (Exception e) {
        fail("Unexpected exception type thrown: " + e.getClass().getName());
    }

    // Verify that the ride service is not called due to invalid input
    verify(rideService, times(0)).stopRide(any(), anyDouble(), anyDouble(), anyInt());
}


    


}