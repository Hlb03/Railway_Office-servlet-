package service;
/*
  User: admin
  Cur_date: 15.08.2022
  Cur_time: 20:13
*/

import dao.DbException;
import entity.Trip;

import java.util.List;

public interface TripService {
    List<Trip> getTrips(int start, int amount) throws DbException;

    int getTripsAmount() throws DbException;

    Trip getById(int id) throws DbException;

    List<Trip> getByRoute(String start, String end) throws DbException;

    void createTrip(Trip t) throws DbException;

    void deleteTrip(Trip t) throws DbException;
}
