package com.geektrust.backend.repositories;

import com.geektrust.backend.entities.Rider;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class RiderRepositoryTest {

    private RiderRepository riderRepository;

    @BeforeEach
    void setUp() {
        riderRepository = new RiderRepository();
    }

    @Test
    void testAddAndGetRider() {
        Rider rider = new Rider.Builder().setId("RIDER001").build();
        riderRepository.addRider(rider);

        Optional<Rider> retrievedRider = riderRepository.getRiderById("RIDER001");
        assertTrue(retrievedRider.isPresent());
        assertEquals(rider, retrievedRider.get());
    }

    @Test
    void testUpdateRider() {
        Rider rider = new Rider.Builder().setId("RIDER001").build();
        riderRepository.addRider(rider);

        Rider updatedRider = new Rider.Builder().setId("RIDER001").setPresentRideId("RIDE123").build();
        riderRepository.updateRider("RIDER001", updatedRider);

        Optional<Rider> retrievedRider = riderRepository.getRiderById("RIDER001");
        assertTrue(retrievedRider.isPresent());
        assertEquals("RIDE123", retrievedRider.get().getPresentRideId());
    }

    @Test
    void testDeleteRider() {
        Rider rider = new Rider.Builder().setId("RIDER001").build();
        riderRepository.addRider(rider);

        riderRepository.deleteRider("RIDER001");
        Optional<Rider> retrievedRider = riderRepository.getRiderById("RIDER001");
        assertFalse(retrievedRider.isPresent());
    }

    // Additional test cases...
}
