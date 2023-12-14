package com.geektrust.backend.commands;

import java.util.List;
import java.util.stream.Collectors;
import com.geektrust.backend.entities.Driver;
import com.geektrust.backend.exceptions.NoDriversAvailableException;
import com.geektrust.backend.exceptions.RideNotValidException;
import com.geektrust.backend.globalConstants.Constants;
import com.geektrust.backend.services.IRideService;
import com.geektrust.backend.services.IRiderService;

public class MatchCommand implements ICommand {

    private final IRideService rideService;
    private final IRiderService riderService;

    public MatchCommand(IRideService rideService, IRiderService riderService) {

        this.rideService = rideService;
        this.riderService = riderService;
    }

    @Override
    public void execute(List<String> tokens) {

        // Get the rider ID from the tokens
        String passengerId = tokens.get(Constants.RIDER_ID_INDEX);

        // Match the rider with available drivers
        List<Driver> correspondingDrivers;
        try {
            correspondingDrivers = rideService.matchRider(passengerId);
        } catch (RideNotValidException e) {
            System.out.println(Constants.INVALID_RIDER_MESSAGE);
            return;
        } catch (NoDriversAvailableException e) {
            System.out.println(Constants.NO_DRIVERS_AVAILABLE_MESSAGE);
            return;
        }

        String driversMatched =
                correspondingDrivers.stream().map(Driver::getId).collect(Collectors.joining(" "));


        // Update the Rider entity with the matched drivers
        try {
            riderService.setCorrespondingDrivers(passengerId, correspondingDrivers);
            System.out.println("DRIVERS_MATCHED " + driversMatched);
        } catch (RideNotValidException e) {
            System.out.println(Constants.INVALID_RIDER_MESSAGE);
            return;
        }

    }
}
