package com.geektrust.backend.utils;

import java.text.DecimalFormat;
import com.geektrust.backend.entities.Location;
import com.geektrust.backend.globalConstants.Constants;

public class GeoLocationUtils {

    private GeoLocationUtils() {

    }

    public static double calculateDistance(Location firstLocation, Location secondLocation) {

        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        double firstLocationX = firstLocation.getXCoordinate();
        double firstLocationY = firstLocation.getYCoordinate();
        double secondLocationX = secondLocation.getXCoordinate();
        double secondLocationY = secondLocation.getYCoordinate();
        double distance = Math.sqrt(Math.pow((secondLocationX - firstLocationX), Constants.POWER_OF_TWO) + Math.pow((secondLocationY - firstLocationY), Constants.POWER_OF_TWO));
        return Double.parseDouble(decimalFormat.format(distance));
    }

   
    
    
}
