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

    Trip getTrip(int tripId) throws DbException;

    List<Trip> getByRoute(String startStation, String endStation, int start, int amount) throws DbException;

    int amountOfFountTripsByRoute(String startStation, String endStation) throws DbException;

    List<Trip> getByRouteAndDate(String start, String end, Date date) throws DbException;

    void createTrip(Trip trip, int startId, int endId, int[] allSettlementsId) throws DbException;

    void deleteTrip(Trip trip) throws DbException;

    void deleteAllOutdatedTrips() throws DbException;

    void updateTripParameters(Trip t) throws DbException;

    void updateAllTripInfo(Trip t, int[] stations) throws DbException;

    Map<Integer, Settlement> getTripSettlements(int id) throws DbException;

    List<Trip> userHasTrips(int userId, int start, int amount) throws DbException;

    int userHasTripsAmount(int userId) throws DbException;
}
