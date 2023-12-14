package com.geektrust.backend.repositories;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.List;
import com.geektrust.backend.entities.Driver;
import com.geektrust.backend.entities.Location;
import com.geektrust.backend.enums.AvailabilityStatus;
import com.geektrust.backend.utils.GeoLocationUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DriverRepositoryTest {

    @Mock
    private GeoLocationUtils geoLocationUtils;

    @InjectMocks
    private DriverRepository driverRepository = new DriverRepository();

    private Driver driver1;
    private Driver driver2;
    private Driver driver3;

    @BeforeEach
    void setUp() throws Exception {
        driver1 = new Driver.Builder().setId("D001").setYourLocation(new Location(0, 0))
                        .setAvailabilityStatus(AvailabilityStatus.AVAILABLE).build();
        driver2 = new Driver.Builder().setId("D002").setYourLocation(new Location(1, 1))
                        .setAvailabilityStatus(AvailabilityStatus.AVAILABLE).build();
        driver3 = new Driver.Builder().setId("D003").setYourLocation(new Location(2, 2))
                        .setAvailabilityStatus(AvailabilityStatus.NOT_AVAILABLE).build();

        driverRepository.addDriver(driver1.getId(), driver1.getYourLocation());
        driverRepository.addDriver(driver2.getId(), driver2.getYourLocation());
        driverRepository.addDriver(driver3.getId(), driver3.getYourLocation());
    }

    @DisplayName("Add driver")
    @Test
    void testAddDriver_AddsDriverCorrectly() {
        Location location = new Location(3, 3);
        String driverId = "D004";
        driverRepository.addDriver(driverId, location);
        assertEquals(driverId, driverRepository.findDriverById(driverId).get().getId());
    }

    @Test
    @DisplayName("Get all available drivers within 5kms")
    public void testGetAvailableDriversWithin5Kms_ReturnsCorrectDrivers() {

        //Arrange
        Driver driver1 = new Driver.Builder().setId("driver1")
        .setYourLocation(new Location(3,1))
        .setAvailabilityStatus(AvailabilityStatus.AVAILABLE).build();
        
        Driver driver2 = new Driver.Builder().setId("driver2")
        .setYourLocation(new Location(5, 6))
        .setAvailabilityStatus(AvailabilityStatus.AVAILABLE).build();
        Driver driver3 = new Driver.Builder().setId("driver3")
        .setYourLocation(new Location(1, 8))
        .setAvailabilityStatus(AvailabilityStatus.AVAILABLE).build();
        Driver driver4 = new Driver.Builder().setId("driver4")
        .setYourLocation(new Location(3, 6))
        .setAvailabilityStatus(AvailabilityStatus.AVAILABLE).build();
        Location location = new Location(2, 7);

        DriverRepository driverRepository = mock(DriverRepository.class);
        when(driverRepository.getAvailableDriversWithin5Kms(location))
            .thenReturn(Arrays.asList(driver3,driver4,driver2));
        
        //Act
        List<Driver> result = driverRepository.getAvailableDriversWithin5Kms(location);

        //Assert
        assertEquals(3, result.size());
        assertEquals(driver3, result.get(0));
        assertEquals(driver4, result.get(1));
        assertEquals(driver2, result.get(2));
    }

    @Test
@DisplayName("Update driver")
void testUpdateDriver_UpdatesDriverCorrectly() {
    // Arrange
    Location newLocation = new Location(4, 4);
    AvailabilityStatus newStatus = AvailabilityStatus.NOT_AVAILABLE;
    String driverId = "D001"; // Existing driver ID

    Driver updatedDriver = new Driver.Builder().setId(driverId)
                                               .setYourLocation(newLocation)
                                               .setAvailabilityStatus(newStatus)
                                               .build();
    
    // Act
    driverRepository.updateDriver(updatedDriver);

    // Assert
    Driver retrievedDriver = driverRepository.findDriverById(driverId).orElse(null);
    assertEquals(newLocation, retrievedDriver.getYourLocation());
    assertEquals(newStatus, retrievedDriver.getAvailabilityStatus());
}


}
