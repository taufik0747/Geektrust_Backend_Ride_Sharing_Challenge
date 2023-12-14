package com.geektrust.backend.commands;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.geektrust.backend.exceptions.InvalidCommandException;
import com.geektrust.backend.exceptions.DriverAlreadyExistsException;
import java.util.Arrays;
import com.geektrust.backend.entities.Driver;
import com.geektrust.backend.entities.Location;
import com.geektrust.backend.repositories.DriverRepository;
import com.geektrust.backend.services.DriverService;
import com.geektrust.backend.services.IDriverService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class AddDriverCommandTest {
    private final IDriverService driverService = new DriverService(new DriverRepository());
    private final ICommand command = new AddDriverCommand(driverService);

    @Test
    public void testExecute_AddsDriverToService() throws Exception {
        // Arrange
        String driverId = "d1";
        int x = 10;
        int y = 20;
        Location location = new Location(x, y);
        String[] inputTokens = {"add_driver", driverId, Integer.toString(x), Integer.toString(y)};

        // Act
        command.execute(Arrays.asList(inputTokens));

        // Assert
        Driver driver = driverService.getDriverById(driverId).orElse(null);
        assertNotNull(driver);
        assertEquals(driverId, driver.getId());
 
    }

    @Test
    public void testExecute_AddsExistingDriver() {
        // Arrange
        String driverId = "d1";
        int x = 10;
        int y = 20;
        String[] inputTokens = {"add_driver", driverId, Integer.toString(x), Integer.toString(y)};

        // Act & Assert
        // First time should add successfully
        Assertions.assertDoesNotThrow(() -> command.execute(Arrays.asList(inputTokens)));
        
        // Second time should throw DriverAlreadyExistsException wrapped in InvalidCommandException
        Exception exception = Assertions.assertThrows(InvalidCommandException.class, 
            () -> command.execute(Arrays.asList(inputTokens)));

        Assertions.assertTrue(exception.getCause() instanceof DriverAlreadyExistsException);
    }

    @Test
    public void testExecute_AddsDriverWithDifferentCoordinates() throws Exception {
        // Arrange
        String driverId1 = "d1";
        int x1 = 10, y1 = 20;
        String driverId2 = "d2";
        int x2 = 30, y2 = 40;
        
        // Act
        command.execute(Arrays.asList("add_driver", driverId1, Integer.toString(x1), Integer.toString(y1)));
        command.execute(Arrays.asList("add_driver", driverId2, Integer.toString(x2), Integer.toString(y2)));
        
        // Assert
        Driver driver1 = driverService.getDriverById(driverId1).orElse(null);
        Driver driver2 = driverService.getDriverById(driverId2).orElse(null);
    
        assertNotNull(driver1);
        assertEquals(driverId1, driver1.getId());
        assertEquals(x1, driver1.getYourLocation().getXCoordinate());
        assertEquals(y1, driver1.getYourLocation().getYCoordinate());
    
        assertNotNull(driver2);
        assertEquals(driverId2, driver2.getId());
        assertEquals(x2, driver2.getYourLocation().getXCoordinate());
        assertEquals(y2, driver2.getYourLocation().getYCoordinate());
    }
    
    @Test
public void testExecute_InvalidCommandFormat() {
    // Arrange
    String driverId = "d1";
    String invalidX = "x"; // Invalid X coordinate (non-integer)
    String invalidY = "20"; // Valid Y coordinate

    // Act & Assert
    Exception exception = Assertions.assertThrows(InvalidCommandException.class, 
        () -> command.execute(Arrays.asList("add_driver", driverId, invalidX, invalidY)));

    // Optionally check the message or type of the underlying exception
}


}