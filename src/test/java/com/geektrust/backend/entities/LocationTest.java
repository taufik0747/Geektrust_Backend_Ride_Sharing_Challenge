package com.geektrust.backend.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LocationTest {
 
    @Test
    void testConstructorAndGetters() {
        double x = 5.0;
        double y = 10.0;
        Location location = new Location(x, y);

        assertEquals(x, location.getXCoordinate(), "X Coordinate should match the provided value");
        assertEquals(y, location.getYCoordinate(), "Y Coordinate should match the provided value");
    }


  

@Test
void testNegativeCoordinates() {
    double x = -5.0;
    double y = -10.0;
    Location location = new Location(x, y);

    assertEquals(x, location.getXCoordinate(), "X Coordinate should handle negative values");
    assertEquals(y, location.getYCoordinate(), "Y Coordinate should handle negative values");
}

}
