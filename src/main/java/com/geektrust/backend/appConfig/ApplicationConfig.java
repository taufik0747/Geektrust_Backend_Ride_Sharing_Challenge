package com.geektrust.backend.appConfig;

import java.io.PrintStream;
import com.geektrust.backend.commands.AddDriverCommand;
import com.geektrust.backend.commands.AddRiderCommand;
import com.geektrust.backend.commands.CommandInvoker;
import com.geektrust.backend.commands.GenerateBillCommand;
import com.geektrust.backend.commands.MatchCommand;
import com.geektrust.backend.commands.StartRideCommand;
import com.geektrust.backend.commands.StopRideCommand;
import com.geektrust.backend.repositories.DriverRepository;
import com.geektrust.backend.repositories.IDriverRepository;
import com.geektrust.backend.repositories.IRideRepository;
import com.geektrust.backend.repositories.IRiderRepository;
import com.geektrust.backend.repositories.RideRepository;
import com.geektrust.backend.repositories.RiderRepository;
import com.geektrust.backend.services.DriverService;
import com.geektrust.backend.services.IDriverService;
import com.geektrust.backend.services.IRideService;
import com.geektrust.backend.services.IRiderService;
import com.geektrust.backend.services.RideService;
import com.geektrust.backend.services.RiderService;
import com.geektrust.backend.utils.RideFareUtils;

public class ApplicationConfig {
 
    //Repositories
    private final IRiderRepository riderRepository = new RiderRepository();
    private final IDriverRepository driverRepository = new DriverRepository();
    private final IRideRepository rideRepository = new RideRepository();

    private final PrintStream output = new PrintStream(System.out);
    private RideFareUtils rideFareUtils = new RideFareUtils(rideRepository);
    private final CommandInvoker commandInvoker = new CommandInvoker();

    //Services
    private final IRiderService riderService = new RiderService(riderRepository);
    private final IDriverService driverService = new DriverService(driverRepository);
    private final IRideService rideService = new RideService(driverRepository, rideRepository, riderRepository, rideFareUtils);

    //Commands
    private final AddDriverCommand addDriverCommand = new AddDriverCommand(driverService);
    private final AddRiderCommand addRiderCommand = new AddRiderCommand(riderService);
    private final MatchCommand matchCommand = new MatchCommand(rideService, riderService);
    private final StartRideCommand startRideCommand = new StartRideCommand(rideService, output);
    private final StopRideCommand stopRideCommand = new StopRideCommand(rideService);
    private final GenerateBillCommand generateBillCommand = new GenerateBillCommand(rideService);

    public CommandInvoker getCommandInvoker() {
       commandInvoker.register("ADD_DRIVER", addDriverCommand);
       commandInvoker.register("ADD_RIDER", addRiderCommand);
       commandInvoker.register("MATCH", matchCommand);
       commandInvoker.register("START_RIDE", startRideCommand);
       commandInvoker.register("STOP_RIDE", stopRideCommand);
       commandInvoker.register("BILL", generateBillCommand);

        return commandInvoker;
    }   

}
