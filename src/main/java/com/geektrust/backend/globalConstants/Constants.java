package com.geektrust.backend.globalConstants;

public class Constants {

    public static final int INDEX = 1;
    public static final double DISTANCE_RADIUS_LIMIT = 5.0;
    public static final int POWER_OF_TWO = 2;
    public static final int MAX_DRIVERS = 5;
    
    public static final int DRIVER_ID_INDEX = 1;
    public static final int LOCATION_XCOORDINATE_INDEX = 2;
    public static final int LOCATION_YCOORDINATE_INDEX = 3;
    public static final int DESTINATION_XCOORDINATE_TOKEN_INDEX = 2;
    public static final int DESTINATION_YCOORDINATE_TOKEN_INDEX = 3;
    public static final int ELAPSED_TIME_TOKEN_INDEX = 4;

    public static final int RIDER_ID_INDEX = 1;
    public static final int LATITUDE_INDEX = 2;
    public static final int LONGITUDE_INDEX = 3;

    public static final int RIDE_ID_TOKEN_INDEX = 1;
    public static final int DRIVER_INDEX_TOKEN_INDEX = 2;
    public static final int RIDER_ID_TOKEN_INDEX = 3;

    public static final int INPUT_FILE_ARG_INDEX = 0;
    public static final int INPUT_FILE_ARG = 1;

    public static final int INPUT_TOKENS_REQUIRED = 2;

    public static final double Two_Decimal_Place = 100.0;
    
    public static final String BILL_FORMAT = "BILL %s %s %.2f\n";
    public static final int ZERO_COST_BILL = 0;

    public static final String NO_DRIVERS_AVAILABLE_MESSAGE = "NO_DRIVERS_AVAILABLE";
    public static final String INVALID_RIDER_MESSAGE = "INVALID_RIDER";
    public static final String INVALID_COMMAND_MESSAGE = "INVALID_COMMAND";
    public static final String COMMAND_NOT_FOUND_MESSAGE = "COMMAND_NOT_FOUND";
    public static final String INVALID_RIDE_MESSAGE = "INVALID_RIDE";
    public static final String INVALID_RIDER_ID_MESSAGE = "INVALID_RIDER_ID";
    public static final String INCOMPLETE_RIDE_MESSAGE = "RIDE_NOT_COMPLETED";

    public static final double BASE_FARE = 50.0;
    public static final double ADDITIONAL_PER_KM_FARE = 6.5;
    public static final double ADDITIONAL_PER_MINUTE_FARE = 2.0;
    public static final double SERVICE_TAX = 0.2;
    
    
}
