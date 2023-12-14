package com.geektrust.backend.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import com.geektrust.backend.enums.AvailabilityStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DriverTest {
   

    @Test
    public void DriverAvailability_test() {
        Location location = new Location(0, 0);
        Driver driver = new Driver.Builder()
                .setId("123")
                .setYourLocation(location)
                .setAvailabilityStatus(AvailabilityStatus.NOT_AVAILABLE)
                .build();

        Assertions.assertFalse(driver.isAvailable());
    }

   


    @Test
    public void testGetAvailabilityReturnsSetAvailability() {
        AvailabilityStatus availability = AvailabilityStatus.AVAILABLE;
        Driver driver = new Driver.Builder()
                .setId("1234")
                .setAvailabilityStatus(availability)
                .build();

        AvailabilityStatus result = driver.getAvailabilityStatus();

        assertEquals(availability, result);
    }

    @Test
    public void testBuilderBuildReturnsValidDriver() {
        String id = "1234";
        Location currentLocation = new Location(1.0, 2.0);
        AvailabilityStatus availability = AvailabilityStatus.AVAILABLE;
        Driver driver = new Driver.Builder()
                .setId(id)
                .setYourLocation(currentLocation)
                .setAvailabilityStatus(availability)
                .build();

        assertNotNull(driver);
        assertEquals(id, driver.getId());
        assertEquals(currentLocation.getXCoordinate(), driver.getYourLocation().getXCoordinate());
        assertEquals(currentLocation.getYCoordinate(), driver.getYourLocation().getYCoordinate());
        assertEquals(availability, driver.getAvailabilityStatus());
    }

 
    

}