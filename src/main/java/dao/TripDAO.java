package dao;
/*
  User: admin
  Cur_date: 15.08.2022
  Cur_time: 19:47
*/

import entity.Settlement;
import entity.Trip;
import entity.User;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public interface TripDAO {

    List<Trip> getAllTrips(int start, int amount) throws DbException;

    int getTripsAmount() throws DbException;

    Trip getTrip(int id, String start, String depart, String end) throws DbException;

    List<Trip> getByRoute(String start, String end) throws DbException;

    Trip getByRouteAndDate(String start, String end, Date date) throws DbException;

    void createTrip(Trip trip, int startId, int endId, Integer[] allSettlementsId) throws DbException;

    void deleteTrip(Trip trip) throws DbException;

    void deleteAllOutdatedTrips() throws DbException;

    Map<Integer, Settlement> getTripSettlements(int id) throws DbException;

    List<Trip> userHasTrips(User user) throws DbException; //, int start, int end
}
