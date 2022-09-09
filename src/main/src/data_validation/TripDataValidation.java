package data_validation;
/*
  User: admin
  Cur_date: 06.09.2022
  Cur_time: 20:42
*/

import entity.Trip;
import entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Date;

public class TripDataValidation {

    private static final Logger LOG = LogManager.getLogger(TripDataValidation.class);

    private static TripDataValidation VALIDATION;

    private TripDataValidation(){}

    public static synchronized TripDataValidation getInstance(){
        if (VALIDATION == null) {
            VALIDATION = new TripDataValidation();
            LOG.trace("TripDataValidator was successfully instantiated");
        }

        return VALIDATION;
    }

    public void checkDataForAllTrainsGet(int start, int end) {
        if (start < 0 || end < 0) {
            LOG.debug("Data validation for getting settlements with limit was failed");
            throw new IllegalArgumentException("Illegal arguments for all trips get with pagination");
        }
    }

    public void checkDataForGetTrip(Trip t) {
        if (t == null || t.getId() < 1) {
            LOG.debug("Data validation failed to find trip");
            throw new IllegalArgumentException("Illegal arguments for trips search");
        }
    }

    public void checkDataForSearchTripByRouteWithLimit(String startStation, String finalStation, int start, int amount) {
        if (startStation == null || finalStation == null
            || startStation.trim().isEmpty() || finalStation.trim().isEmpty() ||
            !startStation.matches("[A-Za-zА-Яа-я-' ]+") || !finalStation.matches("[A-Za-zА-Яа-я-' ]+")
            || start < 0 || amount < 0) {

            LOG.debug("Data validation for getting trips by route with limit was fatal");
            throw new IllegalArgumentException("Illegal arguments for searching trips by route");
        }
    }

    public void checkDataForAmountOfTripsFoundOnRoute(String start, String end) {
        if ( start == null || end == null || start.trim().isEmpty() || end.trim().isEmpty() ||
            !start.matches("[A-Za-zА-Яа-я-' ]+") || !end.matches("[A-Za-zА-Яа-я-' ]+")) {

            LOG.debug("Data validation for taking general amount of trips on certain route was failed");
            throw new IllegalArgumentException("Illegal arguments for getting amount of trips by route");
        }
    }

    public void checkDataForSearchRouteByRouteAndDate(String startStation, String endStation, Date date) {
        if (startStation == null || endStation == null || date == null ||
                startStation.trim().isEmpty() || endStation.trim().isEmpty() ||
                !startStation.matches("[A-Za-zА-Яа-я-' ]+") || !endStation.matches("[A-Za-zА-Яа-я-' ]+")
                || !date.toString().matches("20\\d{2}-((0\\d)|(10)|(11)|(12))-((0[1-9])|([12]\\d)|(3[01]))")) {

            LOG.debug("Data validation for taking all trips from DB by route and date was unaccomplished");
            throw new IllegalArgumentException("Illegal arguments for searching trips by route and date");
        }
    }

    public void checkDataForGettingAllTripsSettlements(int id){
        if (id < 1) {
            LOG.debug("Failed to validate data for taking all trips settlement");
            throw new IllegalArgumentException("Illegal argument to get all station for certain trip");
        }
    }

    public void checkDataForCreatingNewTrip(Trip t, int startSettlementId, int endSettlementId,
                                                   int[] allSettlementsId, String msg){
        checkDataForCreatingNewTripOrUpdatingOldOne(t, msg);

        if (startSettlementId < 1 || endSettlementId < 1) {
            LOG.debug("Data validation for creating new trip was failed at the part of validating start and end settlements id");
            throw new IllegalArgumentException();
        }

        for (int integer : allSettlementsId) {
            if (integer < 1){
                LOG.debug("Data validation for creating new trip was failed in part of all stops adding zone");
                throw new IllegalArgumentException();
            }
        }
    }


    public void checkDataForDeletingTrip(Trip trip) {
        if (trip.getId() < 1 || !trip.getStartStation().matches("[A-Za-zА-Яа-я-' ]+")) {
            LOG.debug("Data validation was unsuccessful for deleting trip part");
            throw new IllegalArgumentException("Illegal arguments for deleting a trip");
        }
    }

    public void checkDataForUpdatingTripInfo(Trip trip) {
        checkDataForCreatingNewTripOrUpdatingOldOne(trip, "Illegal arguments to update trips info");
    }

    public void checkDataForUpdatingTripInfoAndStations(Trip trip, int[] stations) {
        checkDataForCreatingNewTripOrUpdatingOldOne(trip, "Illegal arguments to update trips info and stations");

        for (int i :stations){
            if (i < 1) {
                LOG.debug("Data validation for updating general trips info with stations was failed");
                throw new IllegalArgumentException("Illegal arguments to update trips info and stations");
            }
        }
    }

    public void checkDataForGettingUsersTripsWithLimit(User user, int start, int amount) {
        if (user == null || user.getId() < 0 || start < 0 || amount < 0) {
            LOG.debug("Data validation for taking users trips from DB was failed");
            throw new IllegalArgumentException("Illegal arguments to get all users trip");
        }
    }

    public void checkDataForGettingUsersTripAmount(User user) {
        if (user == null || user.getId() < 0) {
            LOG.debug("Data validation counting users bought tickets amount was failed");
            throw new IllegalArgumentException("Illegal arguments to get amount of users trip");
        }
    }

    private void checkDataForCreatingNewTripOrUpdatingOldOne(Trip t, String msg) {

        if (t.getCost() == null || t.getStartStation() == null || t.getFinalStation() == null || t.getTrain() == null ||
                t.getDepartureDate() == null || t.getDepartureTime() == null || t.getArrivalDate() == null || t.getArrivalTime() == null ||
                !t.getDepartureDate().toString().matches("20\\d{2}-((0\\d)|(10)|(11)|(12))-((0[1-9])|([12]\\d)|(3[01]))") ||
                !t.getDepartureTime().toString().matches("(0\\d|1\\d|2[0-3]):[0-5]\\d:[0-5]\\d") ||
                !t.getArrivalDate().toString().matches("20\\d{2}-((0\\d)|(10)|(11)|(12))-((0[1-9])|([12]\\d)|(3[01]))") ||
                !t.getArrivalTime().toString().matches("(0\\d|1\\d|2[0-3]):[0-5]\\d:[0-5]\\d") ||
                t.getSeats() < 20 || t.getCost().compareTo(new BigDecimal("200.00")) < 0 || t.getTrain().getId() < 0
                || t.getStartStation().length() > 30 || t.getFinalStation().length() > 30 ||
                t.getStartStation().trim().isEmpty() || t.getFinalStation().trim().isEmpty()
        ) {

            LOG.debug("Data validation was fatal at the moment of validation trips data");
            throw new IllegalArgumentException(msg);
        }

    }
}
