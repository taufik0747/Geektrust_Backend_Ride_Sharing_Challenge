    package com.geektrust.backend.entities;

    import com.geektrust.backend.enums.RideStatus;
    import org.junit.jupiter.api.Test;
    import static org.junit.jupiter.api.Assertions.*;
    import static org.mockito.Mockito.mock;
    import java.time.LocalDateTime;

    class RideTest {

    @Test
    void testRideConstruction() {
        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = startTime.plusHours(1);
        double totalBill = 150.0;
        int elapsedTime = 60; // in minutes

        // Creating a Ride instance using the builder
        Ride ride = new Ride.Builder()
            .setId("RIDE123")
            // Set other fields with dummy data
            .setStartTime(startTime)
            .setEndTime(endTime)
            .setTotalBill(totalBill)
            .setElapsedTime(elapsedTime)
            .setRideStatus(RideStatus.COMPLETED)
            .build();

        // Assertions
        assertEquals("RIDE123", ride.getId());
        assertEquals(startTime, ride.getStartTime());
        assertEquals(endTime, ride.getEndTime());
        assertEquals(totalBill, ride.getTotalBill());
        assertEquals(elapsedTime, ride.getElapsedTime());
        assertEquals(RideStatus.COMPLETED, ride.getRideStatus());
        // Add assertions for other fields like passenger, driver, locations
    }

    @Test
    void testRideConstructionWithAllFields() {
    // Setup (create mock Rider, Driver, and Locations)
    Rider mockRider = mock(Rider.class);
    Driver mockDriver = mock(Driver.class);
    Location source = new Location(1.0, 1.0);
    Location destination = new Location(2.0, 2.0);
    LocalDateTime startTime = LocalDateTime.now();
    LocalDateTime endTime = startTime.plusHours(2);
    double totalBill = 200.0;
    int elapsedTime = 120;
    RideStatus status = RideStatus.COMPLETED;

    // Creating a Ride instance using the builder
    Ride ride = new Ride.Builder()
        .setId("RIDE123")
        .setPassenger(mockRider)
        .setDriver(mockDriver)
        .setSourceLocation(source)
        .setDestination(destination)
        .setStartTime(startTime)
        .setEndTime(endTime)
        .setTotalBill(totalBill)
        .setElapsedTime(elapsedTime)
        .setRideStatus(status)
        .build();

    // Assertions
    assertEquals("RIDE123", ride.getId());
    assertEquals(mockRider, ride.getPassenger());
    assertEquals(mockDriver, ride.getDriver());
    assertEquals(source, ride.getSourceLocation());
    assertEquals(destination, ride.getDestination());
    assertEquals(startTime, ride.getStartTime());
    assertEquals(endTime, ride.getEndTime());
    assertEquals(totalBill, ride.getTotalBill());
    assertEquals(elapsedTime, ride.getElapsedTime());
    assertEquals(status, ride.getRideStatus());
    }

    @Test
    void testRideConstructionWithMinimumFields() {
    // Assuming ID and startTime are required fields
    String id = "RIDE124";
    LocalDateTime startTime = LocalDateTime.now();

    // Creating a Ride instance with minimum fields
    Ride ride = new Ride.Builder()
        .setId(id)
        .setStartTime(startTime)
        .build();

    // Assertions
    assertEquals(id, ride.getId());
    assertEquals(startTime, ride.getStartTime());
    // Check other fields for default/null values
    assertNull(ride.getPassenger());
    assertNull(ride.getDriver());
    // ... Continue for other fields
    }


}