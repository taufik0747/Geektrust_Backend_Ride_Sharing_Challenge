package com.geektrust.backend.commands;



import java.util.List;
import java.util.Optional;
import com.geektrust.backend.entities.Ride;
import com.geektrust.backend.globalConstants.Constants;
import com.geektrust.backend.services.IRideService;


public class GenerateBillCommand implements ICommand{

   
   private final IRideService rideService;

   public GenerateBillCommand(IRideService rideService) {
    this.rideService = rideService;
}
    @Override
    public void execute(List<String> tokens) {
        try {
            if (tokens.size() != Constants.INPUT_TOKENS_REQUIRED) {
               throw new Exception(Constants.INVALID_COMMAND_MESSAGE);
            }
            
            String rideId = tokens.get(Constants.RIDE_ID_TOKEN_INDEX);
            double bill = rideService.generateBill(rideId);
   
            if (bill ==  Constants.ZERO_COST_BILL) {
               return;
            }
   
            Optional<Ride> ride = rideService.getRideById(rideId);
            if (!ride.isPresent()) {
               throw new Exception(Constants.INVALID_RIDE_MESSAGE);
            }
   
            Ride currentRide = ride.get();
            String driverId = currentRide.getDriver().getId();
   
            System.out.printf(Constants.BILL_FORMAT, rideId, driverId, bill);
         } catch (Exception e) {
            System.out.println(Constants.INVALID_RIDE_MESSAGE);
         }
      }
    }
        
    