package service;
/*
  User: admin
  Cur_date: 15.08.2022
  Cur_time: 20:15
*/

import dao.DbException;
import dao.TripDAO;
import data_validation.TripDataValidation;
import entity.Settlement;
import entity.Trip;
import entity.User;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public class TripServiceImpl implements TripService {

    private final TripDAO dao;

    private final static TripDataValidation VALIDATION = TripDataValidation.getInstance();

    public TripServiceImpl(TripDAO dao){
        this.dao = dao;
    }

    @Override
    public List<Trip> getTrips(int start, int amount) throws DbException {
        VALIDATION.checkDataForAllTrainsGet(start, amount);

        return dao.getAllTrips(start, amount);
    }

    @Override
    public int getTripsAmount() throws DbException {
        return dao.getTripsAmount();
    }

    @Override
    public Trip getTrip(Trip trip) throws DbException {
        VALIDATION.checkDataForGetTrip(trip);

        return dao.getTrip(trip);
    }

    @Override
    public List<Trip> getByRoute(String startStation, String endStation, int start, int amount) throws DbException {
        VALIDATION.checkDataForSearchTripByRouteWithLimit(startStation, endStation, start, amount);

        return dao.getByRoute(startStation, endStation, start, amount);
    }

    @Override
    public int amountOfFoundTripsByRoute(String startStation, String endStation) throws DbException{
        VALIDATION.checkDataForAmountOfTripsFoundOnRoute(startStation, endStation);

        return dao.amountOfFountTripsByRoute(startStation, endStation);
    }

    @Override
    public List<Trip> getByRouteAndDate(String start, String end, Date date) throws DbException {
        VALIDATION.checkDataForSearchRouteByRouteAndDate(start, end, date);

        return dao.getByRouteAndDate(start, end, date);
    }

    @Override
    public Map<Integer, Settlement> tripContainsSettlements(int id) throws DbException {
        VALIDATION.checkDataForGettingAllTripsSettlements(id);

        return dao.getTripSettlements(id);
    }

    @Override
    public void createTrip(Trip t, int startSettlementId, int endSettlementId, int[] allSettlementsId) throws DbException {
        VALIDATION.checkDataForCreatingNewTrip(t, startSettlementId, endSettlementId, allSettlementsId,
                "Illegal arguments to create new trip");

        dao.createTrip(t, startSettlementId, endSettlementId, allSettlementsId);
    }

    @Override
    public void deleteTrip(Trip t) throws DbException {
        VALIDATION.checkDataForDeletingTrip(t);

        dao.deleteTrip(t);
    }

    @Override
    public void deleteAllOutdatedTrips() throws DbException {
        dao.deleteAllOutdatedTrips();
    }

    @Override
    public void updateTripParameters(Trip trip) throws DbException {
        VALIDATION.checkDataForUpdatingTripInfo(trip);

        dao.updateTripParameters(trip);
    }

    @Override
    public void updateAllTripInfo(Trip trip, int[] stations) throws DbException {
        VALIDATION.checkDataForUpdatingTripInfoAndStations(trip, stations);

        dao.updateAllTripInfo(trip, stations);
    }

    @Override
    public List<Trip> userHasTrips(User user, int start, int amount) throws DbException {
        VALIDATION.checkDataForGettingUsersTripsWithLimit(user, start, amount);

        return dao.userHasTrips(user, start, amount);
    }

    @Override
    public int userHasTripsAmount(User u) throws DbException{
        VALIDATION.checkDataForGettingUsersTripAmount(u);

        return dao.userHasTripsAmount(u);
    }
}
