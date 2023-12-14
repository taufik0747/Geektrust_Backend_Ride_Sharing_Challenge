package com.geektrust.backend.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import com.geektrust.backend.entities.Driver;
import com.geektrust.backend.entities.Location;
import com.geektrust.backend.entities.Rider;
import com.geektrust.backend.enums.AvailabilityStatus;
import com.geektrust.backend.repositories.IRiderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RiderServiceTest {

    @Mock
    private IRiderRepository riderRepository;

    @InjectMocks
    private RiderService riderService;

    private String passengerId;
    private Location location;
    private Rider passenger;
    private List<Driver> drivers;

    @BeforeEach
    public void setup() {
        passengerId = "R01";
        location = new Location(100, 100);
        passenger = new Rider.Builder().setId(passengerId).setYourLocation(location).build();

        drivers = Arrays.asList(new Driver.Builder().setId("D01").setYourLocation(new Location(100, 90)).setAvailabilityStatus(AvailabilityStatus.AVAILABLE).build());
    }

    @Test
    @DisplayName("Add Rider")
    public void testAddRider() {
        when(riderRepository.getRiderById(passengerId)).thenReturn(Optional.empty());
        riderService.addRider(passengerId, location.getXCoordinate(), location.getYCoordinate());
        verify(riderRepository, times(1)).addRider(any(Rider.class));
    }

    @Test
    @DisplayName("Get Rider By Id")
    public void testGetRiderById() {
        when(riderRepository.getRiderById(passengerId)).thenReturn(Optional.of(passenger));
        Optional<Rider> returnedRider = riderService.getRiderById(passengerId);
        assertTrue(returnedRider.isPresent());
        assertEquals(passenger, returnedRider.get());
    }

    @Test
    @DisplayName("Get non-existing rider by id")
    void getNonExistingRiderById() {
        Mockito.when(riderRepository.getRiderById("R002"))
                .thenReturn(Optional.empty());

        Optional<Rider> rider = riderService.getRiderById("R002");

        Assertions.assertFalse(rider.isPresent());
    }
}

