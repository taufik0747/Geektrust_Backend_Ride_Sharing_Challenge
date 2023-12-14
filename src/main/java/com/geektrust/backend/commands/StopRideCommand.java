package com.geektrust.backend.commands;

import java.util.List;
import com.geektrust.backend.exceptions.RideNotValidException;
import com.geektrust.backend.globalConstants.Constants;
import com.geektrust.backend.services.IRideService;

public class StopRideCommand implements ICommand {

    private final IRideService rideService;

    public StopRideCommand(IRideService rideService) {
        this.rideService = rideService;
    }

    @Override
    public void execute(List<String> tokens) {
        String rideId = tokens.get(Constants.RIDE_ID_TOKEN_INDEX);
        double destinationXCoordinate = Double.parseDouble(tokens.get(Constants.DESTINATION_XCOORDINATE_TOKEN_INDEX));
        double destinationYCoordinate = Double.parseDouble(tokens.get(Constants.DESTINATION_YCOORDINATE_TOKEN_INDEX));
        int elapsedTime = Integer.parseInt(tokens.get(Constants.ELAPSED_TIME_TOKEN_INDEX));

        try {
            String output = rideService.stopRide(rideId, destinationXCoordinate, destinationYCoordinate, elapsedTime);
            System.out.println(output);
        } catch(RideNotValidException e) {
            System.out.println(Constants.INVALID_RIDE_MESSAGE);
        }
    }    
}
