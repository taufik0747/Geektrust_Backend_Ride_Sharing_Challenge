package com.geektrust.backend.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import com.geektrust.backend.entities.Rider;
import com.geektrust.backend.repositories.RiderRepository;
import com.geektrust.backend.services.RiderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class AddRiderCommandTest {
    private RiderService riderService;
    private ICommand command;

    @BeforeEach
    void setUp() {
        riderService = new RiderService(new RiderRepository());
        command = new AddRiderCommand(riderService);
    }

    @Test
    void shouldAddRider() throws Exception {
        List<String> tokens = Arrays.asList("","rider-1", "12.34", "56.78");
        command.execute(tokens);

        Optional<Rider> crider = riderService.getRiderById("rider-1");
        Rider rider =crider.get();
        assertNotNull(crider);
        assertEquals("rider-1", rider.getId());
        assertEquals(12.34, rider.getYourLocation().getXCoordinate());
        assertEquals(56.78, rider.getYourLocation().getYCoordinate());
    }

    @Test
    void shouldThrowExceptionForInvalidCoordinates() {
        List<String> tokens = Arrays.asList("", "rider-1", "invalidLat", "invalidLong");

        Exception exception = Assertions.assertThrows(IllegalArgumentException.class, 
            () -> command.execute(tokens));

        Assertions.assertTrue(exception.getMessage().contains("Latitude and Longitude must be valid numbers"));
    }

    @Test
void shouldAcceptUnrealisticCoordinates() {
    String latitude = "100.0"; // Out of range latitude
    String longitude = "200.0"; // Out of range longitude
    List<String> tokens = Arrays.asList("", "rider-2", latitude, longitude);

    Assertions.assertDoesNotThrow(() -> command.execute(tokens));
}

}