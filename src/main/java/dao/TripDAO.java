package dao;
/*
  User: admin
  Cur_date: 15.08.2022
  Cur_time: 19:47
*/

import entity.Trip;

import java.util.Date;
import java.util.List;

public interface TripDAO {

    List<Trip> getAllTrips(int start, int amount) throws DbException;

    int getTripsAmount() throws DbException;

    Trip getById(int id) throws DbException;

    List<Trip> getByRoute(String start, String end) throws DbException;

    Trip getByRouteAndDate(String start, String end, Date date) throws DbException;

    void createTrip(Trip trip) throws DbException;

    void deleteTrip(Trip trip) throws DbException;
}
