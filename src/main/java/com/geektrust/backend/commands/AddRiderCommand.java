package com.geektrust.backend.commands;

import java.util.List;
import com.geektrust.backend.globalConstants.Constants;
import com.geektrust.backend.services.IRiderService;

public class AddRiderCommand implements ICommand {

    private final IRiderService riderService;

    public AddRiderCommand(IRiderService riderService) {
        this.riderService = riderService;
    }

    @Override
    public void execute(List<String> tokens) {
        
        String passengerId = tokens.get(Constants.RIDER_ID_INDEX);
        double latitude;
        double longitude;

        try {
            latitude = Double.parseDouble(tokens.get(Constants.LATITUDE_INDEX));
            longitude = Double.parseDouble(tokens.get(Constants.LONGITUDE_INDEX));
        } 
        catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid command. Latitude and Longitude must be valid numbers.", e);
        }

        riderService.addRider(passengerId, latitude, longitude);

    }
    
}
