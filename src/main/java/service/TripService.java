package service;
/*
  User: admin
  Cur_date: 15.08.2022
  Cur_time: 20:13
*/

import dao.DbException;
import entity.Settlement;
import entity.Trip;
import exception.FailedDeleteException;
import exception.FailedInsertException;

import java.util.List;
import java.util.Map;

public interface TripService {
    List<Trip> getTrips(int start, int amount) throws DbException;

    int getTripsAmount() throws DbException;

    Trip getTrip(int id, String start, String depart , String end) throws DbException; //Date depDate, Time depTime

    List<Trip> getByRoute(String start, String end) throws DbException;

    Map<Integer, Settlement> tripContainsSettlements(int id) throws DbException;

    void createTrip(Trip t, int startId, int endId, Integer[] allSettlementsId) throws DbException, FailedInsertException;

    void deleteTrip(Trip t) throws DbException, FailedDeleteException;
}
