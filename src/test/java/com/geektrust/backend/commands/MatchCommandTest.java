package com.geektrust.backend.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import com.geektrust.backend.entities.Driver;
import com.geektrust.backend.exceptions.NoDriversAvailableException;
import com.geektrust.backend.exceptions.RideNotValidException;
import com.geektrust.backend.globalConstants.Constants;
import com.geektrust.backend.services.IRideService;
import com.geektrust.backend.services.IRiderService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class MatchCommandTest {
    
    @Mock
    private IRideService rideService;

    @Mock
    private IRiderService riderService;

    private ICommand matchCommand;

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        matchCommand = new MatchCommand(rideService, riderService);
    }

    @BeforeEach
    void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
    }

   @Test
    void testExecute_SuccessfulMatch() throws Exception { // Declare that the test method may throw an Exception
        String passengerId = "RIDER123";
        Driver driver1 = mock(Driver.class);
        Driver driver2 = mock(Driver.class);
        when(driver1.getId()).thenReturn("D1");
        when(driver2.getId()).thenReturn("D2");
        List<Driver> drivers = Arrays.asList(driver1, driver2);

        when(rideService.matchRider(passengerId)).thenReturn(drivers);
        
        matchCommand.execute(Arrays.asList("MATCH", passengerId));

        verify(rideService).matchRider(passengerId);
        verify(riderService).setCorrespondingDrivers(passengerId, drivers);
        // Capture and assert the System.out print statements
    }


    @Test
    void testExecute_NoDriversAvailable() throws Exception {
        String passengerId = "RIDER123";
        when(rideService.matchRider(passengerId)).thenThrow(new NoDriversAvailableException());
    
        matchCommand.execute(Arrays.asList("MATCH", passengerId));
    
        // Assertions to check output
        String expectedOutput = Constants.NO_DRIVERS_AVAILABLE_MESSAGE;
        assertTrue(outContent.toString().contains(expectedOutput));
    }
    
    @Test
    void testExecute_InvalidRiderId() throws Exception {
        String invalidPassengerId = "INVALID_RIDER";
        when(rideService.matchRider(invalidPassengerId)).thenThrow(new RideNotValidException());
    
        matchCommand.execute(Arrays.asList("MATCH", invalidPassengerId));
    
        // Assertions to check output
        String expectedOutput = Constants.INVALID_RIDER_MESSAGE;
        assertTrue(outContent.toString().contains(expectedOutput));
    }
    


    
}
