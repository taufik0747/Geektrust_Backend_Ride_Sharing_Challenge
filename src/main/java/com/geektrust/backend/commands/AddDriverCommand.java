package com.geektrust.backend.commands;

import java.util.List;
import com.geektrust.backend.entities.Location;
import com.geektrust.backend.exceptions.DriverAlreadyExistsException;
import com.geektrust.backend.exceptions.InvalidCommandException;
import com.geektrust.backend.globalConstants.Constants;
import com.geektrust.backend.services.IDriverService;

public class AddDriverCommand implements ICommand {

    private final IDriverService driverService;

    public AddDriverCommand(IDriverService driverService) {
        this.driverService = driverService;
    }

    @Override
    public void execute(List<String> tokens) throws InvalidCommandException {
        try {
            String driverId = tokens.get(Constants.DRIVER_ID_INDEX);
            int xCoordinate = Integer.parseInt(tokens.get(Constants.LOCATION_XCOORDINATE_INDEX));
            int yCoordinate = Integer.parseInt(tokens.get(Constants.LOCATION_YCOORDINATE_INDEX));
            Location yourLocation = new Location(xCoordinate, yCoordinate);
            driverService.addDriver(driverId, yourLocation);

        }
         
        catch(DriverAlreadyExistsException e) {
            throw new InvalidCommandException("Driver already exists!", e);
        }

        catch(Exception e) {
            throw new InvalidCommandException("Error on driver addition!" , e);
                
        }
    }
    
}
