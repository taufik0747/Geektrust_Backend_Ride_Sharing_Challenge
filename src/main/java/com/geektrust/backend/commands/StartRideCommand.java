package com.geektrust.backend.commands;

import java.io.PrintStream;
import java.util.List;
import java.util.Optional;
import com.geektrust.backend.entities.Ride;
import com.geektrust.backend.exceptions.NoDriversAvailableException;
import com.geektrust.backend.exceptions.RideNotValidException;
import com.geektrust.backend.globalConstants.Constants;
import com.geektrust.backend.services.IRideService;

public class StartRideCommand implements ICommand {

    private final IRideService rideService;
    private final PrintStream output;

    public StartRideCommand(IRideService rideService, PrintStream output) {
        this.rideService = rideService;
        this.output = output;
    }

    @Override
    public void execute(List<String> tokens) {
        String rideId = tokens.get(Constants.RIDE_ID_TOKEN_INDEX);
        String driverId = tokens.get(Constants.DRIVER_INDEX_TOKEN_INDEX);
        String passengerId = tokens.get(Constants.RIDER_ID_TOKEN_INDEX);

        try {
            Optional<Ride> ride = rideService.startRide(rideId, driverId, passengerId);
            if(ride.isPresent()) {
                output.println("RIDE_STARTED " + ride.get().getId());
            }
        
        } catch(RideNotValidException | NoDriversAvailableException e) {
            output.println(Constants.INVALID_RIDE_MESSAGE);
        }

    }
    
}
