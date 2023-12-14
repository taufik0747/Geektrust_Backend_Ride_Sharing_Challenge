package com.geektrust.backend.repositories;



import static org.junit.jupiter.api.Assertions.assertFalse;
import java.time.LocalDateTime;
import java.util.Optional;
import com.geektrust.backend.entities.Driver;
import com.geektrust.backend.entities.Location;
import com.geektrust.backend.entities.Ride;
import com.geektrust.backend.entities.Rider;
import com.geektrust.backend.enums.AvailabilityStatus;
import com.geektrust.backend.enums.RideStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RideRepositoryTest {

    private IRideRepository rideRepository;
    private DriverRepository driverRepository;
    private RiderRepository riderRepository;

    @BeforeEach
    public void setUp() {
        rideRepository = new RideRepository();
        driverRepository = new DriverRepository();
        riderRepository = new RiderRepository();
    }

    @Test
    public void testAddAndGetRideById() {
        // create a rider
        Rider passenger = new Rider.Builder()
        .setId("rider1")
        .setYourLocation(new Location(1.0, 2.0))
        .build();
        
        riderRepository.addRider(passenger);

        
        Driver driver = new Driver.Builder()
        .setId("driver1")
        .setYourLocation(new Location(3.0, 4.0))
        .setAvailabilityStatus(AvailabilityStatus.AVAILABLE)
        .build();
        
        driverRepository.addDriver(driver.getId(),new Location(3.0, 4.0));

        
        LocalDateTime startTime = LocalDateTime.now();
        Ride ride = new Ride.Builder().setId("ride1").setPassenger(passenger).setDriver(driver)
                .setSourceLocation(new Location(1.0, 2.0)).setRideStatus(RideStatus.STARTED)
                .setStartTime(startTime).build();

        
        rideRepository.addRide(ride);

        
        Optional<Ride> optionalRide = rideRepository.getRideById("ride1");

        
        Assertions.assertTrue(optionalRide.isPresent());
        Assertions.assertEquals(passenger, optionalRide.get().getPassenger());
        Assertions.assertEquals(driver, optionalRide.get().getDriver());
    }

    @Test
    public void testUpdateRide() {
        // Setup
        Ride ride = createTestRide("ride1", "rider1", "driver1");
        rideRepository.addRide(ride);

        // Update the ride
        Ride updatedRide = new Ride.Builder().setId("ride1") // other updated details
                                              .build();
        rideRepository.updateRide(updatedRide);

        // Retrieve and verify
        Optional<Ride> result = rideRepository.getRideById("ride1");
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(updatedRide, result.get()); // Or specific fields if appropriate
    }


        private Ride createTestRide(String rideId, String riderId, String driverId) {
            // Create dummy locations for start and destination
            Location startLocation = new Location(1.0, 1.0); // Example start location
            Location endLocation = new Location(2.0, 2.0); // Example end location
    
            // Create dummy Rider and Driver
            Rider rider = new Rider.Builder()
                            .setId(riderId)
                            .setYourLocation(startLocation)
                            .build();
            Driver driver = new Driver.Builder()
                            .setId(driverId)
                            .setYourLocation(startLocation)
                            .setAvailabilityStatus(AvailabilityStatus.AVAILABLE)
                            .build();
    
            // Create dummy start and end times
            LocalDateTime startTime = LocalDateTime.now();
            LocalDateTime endTime = LocalDateTime.now().plusHours(1); // 1 hour later
    
            // Build and return the Ride object
            return new Ride.Builder()
                    .setId(rideId)
                    .setPassenger(rider)
                    .setDriver(driver)
                    .setSourceLocation(startLocation)
                    .setDestination(endLocation)
                    .setStartTime(startTime)
                    .setEndTime(endTime)
                    .setRideStatus(RideStatus.STARTED)
                    .build();
        }

        @Test
        void testDeleteRider_RemovesRiderCorrectly() {
            // Arrange
            Rider rider = new Rider.Builder().setId("rider2").build();
            riderRepository.addRider(rider);
        
            // Act
            riderRepository.deleteRider("rider2");
        
            // Assert
            assertFalse(riderRepository.getRiderById("rider2").isPresent());
        }
        


}
    

