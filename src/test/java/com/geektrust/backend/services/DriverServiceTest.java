package com.geektrust.backend.services;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;
import com.geektrust.backend.entities.Driver;
import com.geektrust.backend.entities.Location;
import com.geektrust.backend.enums.AvailabilityStatus;
import com.geektrust.backend.exceptions.DriverAlreadyExistsException;
import com.geektrust.backend.repositories.IDriverRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class DriverServiceTest {
    
    @Test
    @DisplayName("Add driver successfully")
    void addDriverTest() throws DriverAlreadyExistsException {
        // Arrange
        IDriverRepository driverRepository = mock(IDriverRepository.class);
        DriverService driverService = new DriverService(driverRepository);

        String driverId = "D1";
        Location location = new Location(1, 1);

        // Act
        driverService.addDriver(driverId, location);

        // Assert
        verify(driverRepository, times(1)).addDriver(driverId, location);
    }
    @Test
    @DisplayName("Add driver with existing ID throws exception")
    void addDriverWithExistingIdTest() throws DriverAlreadyExistsException {
        // Arrange
        IDriverRepository driverRepository = mock(IDriverRepository.class);
        DriverService driverService = new DriverService(driverRepository);

        String driverId = "D1";
        Location location = new Location(1, 1);

        doThrow(DriverAlreadyExistsException.class).when(driverRepository).addDriver(driverId, location);

        // Act & Assert
        assertThrows(DriverAlreadyExistsException.class, () -> driverService.addDriver(driverId, location));
    }

    @Test
    @DisplayName("Get driver by ID returns correct driver")
    void getDriverByIdTest() {
        // Arrange
        IDriverRepository driverRepository = mock(IDriverRepository.class);
        DriverService driverService = new DriverService(driverRepository);

        String driverId = "D1";
        Location location = new Location(1, 1);

        Driver expectedDriver = new Driver.Builder().setId(driverId).setYourLocation(location).setAvailabilityStatus(AvailabilityStatus.AVAILABLE).build();

        when(driverRepository.findDriverById(driverId)).thenReturn(Optional.of(expectedDriver));

        // Act
        Optional<Driver> actualDriver = driverService.getDriverById(driverId);

        // Assert
        assertTrue(actualDriver.isPresent());
        assertEquals(expectedDriver, actualDriver.get());
    }
}
