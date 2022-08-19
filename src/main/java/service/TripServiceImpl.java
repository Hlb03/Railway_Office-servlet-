package service;
/*
  User: admin
  Cur_date: 15.08.2022
  Cur_time: 20:15
*/

import dao.DbException;
import dao.TripDAO;
import dao.TripDAOImpl;
import entity.Trip;

import java.util.List;

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
    public Trip getById(int id) throws DbException {
        return dao.getById(id);
    }

    @Override
    public List<Trip> getByRoute(String start, String end) throws DbException {
        return dao.getByRoute(start, end);
    }

    @Override
    public void createTrip(Trip t) throws DbException {
        dao.createTrip(t);
    }

    @Override
    public void deleteTrip(Trip t) throws DbException {
        dao.deleteTrip(t);
    }
}
