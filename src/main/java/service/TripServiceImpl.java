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
    public Trip getTrip(int id, String start, String depart, String end) throws DbException {
        return dao.getTrip(id, start, depart, end);
    }

    @Override
    public List<Trip> getByRoute(String start, String end) throws DbException {
        return dao.getByRoute(start, end);
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
    public List<Trip> userHasTrips(User user) throws DbException { //, int start, int end
        return dao.userHasTrips(user); //, start, end
    }
}
