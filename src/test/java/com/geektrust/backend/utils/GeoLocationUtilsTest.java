package com.geektrust.backend.utils;

import com.geektrust.backend.entities.Location;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GeoLocationUtilsTest {

    @Test
    void testCalculateDistance() {
        Location loc1 = new Location(0, 0);
        Location loc2 = new Location(3, 4);

        double distance = GeoLocationUtils.calculateDistance(loc1, loc2);
        assertEquals(5.0, distance); // 3-4-5 triangle
    }

    @Test
    void testDistanceRounding() {
        Location loc1 = new Location(0, 0);
        Location loc2 = new Location(1, 1);

        double distance = GeoLocationUtils.calculateDistance(loc1, loc2);
        assertEquals(1.41, distance); // sqrt(2) rounded to 2 decimal places
    }

    @Test
    void testSameLocationDistance() {
        Location loc1 = new Location(1, 1);
        Location loc2 = new Location(1, 1);

        double distance = GeoLocationUtils.calculateDistance(loc1, loc2);
        assertEquals(0.0, distance);
    }

    // Additional test cases for other edge cases...
}
