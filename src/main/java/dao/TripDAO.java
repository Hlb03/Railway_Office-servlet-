package dao;
/*
  User: admin
  Cur_date: 15.08.2022
  Cur_time: 19:47
*/

import entity.Settlement;
import entity.Trip;
import exception.FailedDeleteException;
import exception.FailedInsertException;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public interface TripDAO {

    List<Trip> getAllTrips(int start, int amount) throws DbException;

    int getTripsAmount() throws DbException;

    Trip getTrip(int id, String start, String depart, String end) throws DbException;

    List<Trip> getByRoute(String start, String end) throws DbException;

    Trip getByRouteAndDate(String start, String end, Date date) throws DbException;

    void createTrip(Trip trip, int startId, int endId, Integer[] allSettlementsId) throws DbException, FailedInsertException;

    void deleteTrip(Trip trip) throws DbException, FailedDeleteException;

    Map<Integer, Settlement> getTripSettlements(int id) throws DbException;
}
