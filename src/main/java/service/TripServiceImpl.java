package service;
/*
  User: admin
  Cur_date: 15.08.2022
  Cur_time: 20:15
*/

import dao.DbException;
import dao.TripDAO;
import entity.Settlement;
import entity.Trip;
import entity.User;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public class TripServiceImpl implements TripService {

    private final TripDAO dao;

    public TripServiceImpl(TripDAO dao){
        this.dao = dao;
    }

    @Override
    public List<Trip> getTrips(int start, int amount) throws DbException {
        return dao.getAllTrips(start, amount);
    }

    @Override
    public int getTripsAmount() throws DbException {
        return dao.getTripsAmount();
    }

    @Override
    public Trip getTrip(Trip trip) throws DbException { //int id, String start, String depart, String end
        return dao.getTrip(trip);
    }

    @Override
    public List<Trip> getByRoute(String startStation, String endStation, int start, int amount) throws DbException {
        return dao.getByRoute(startStation, endStation, start, amount);
    }

    @Override
    public int amountOfFountTripsByRoute(String startStation, String endStation) throws DbException{
        return dao.amountOfFountTripsByRoute(startStation, endStation);
    }

    @Override
    public List<Trip> getByRouteAndDate(String start, String end, Date date) throws DbException {
        return dao.getByRouteAndDate(start, end, date);
    }

    @Override
    public Map<Integer, Settlement> tripContainsSettlements(int id) throws DbException {
        return dao.getTripSettlements(id);
    }

    @Override
    public void createTrip(Trip t, int startSettlementId, int endSettlementId, Integer[] allSettlementsId) throws DbException {
        dao.createTrip(t, startSettlementId, endSettlementId, allSettlementsId);
    }

    @Override
    public void deleteTrip(Trip t) throws DbException {
        dao.deleteTrip(t);
    }

    @Override
    public void deleteAllOutdatedTrips() throws DbException {
        dao.deleteAllOutdatedTrips();
    }

    @Override
    public void updateTripParameters(Trip trip) throws DbException {
        dao.updateTripParameters(trip);
    }

    @Override
    public void updateAllTripInfo(Trip trip, int[] stations) throws DbException {
        dao.updateAllTripInfo(trip, stations);
    }

    @Override
    public List<Trip> userHasTrips(User user, int start, int amount) throws DbException { //, int start, int end
        return dao.userHasTrips(user, start, amount); //, start, end
    }

    @Override
    public int userHasTripsAmount(User u) throws DbException{
        return dao.userHasTripsAmount(u);
    }
}
