package service;
/*
  User: admin
  Cur_date: 15.08.2022
  Cur_time: 20:13
*/

import dao.DbException;
import entity.Settlement;
import entity.Trip;
import entity.User;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public interface TripService {
    List<Trip> getTrips(int start, int amount) throws DbException;

    int getTripsAmount() throws DbException;

    Trip getTrip(Trip trip) throws DbException;

    List<Trip> getByRoute(String startStation, String endStation, int start, int amount) throws DbException;

    int amountOfFoundTripsByRoute(String startStation, String endStation) throws DbException;

    List<Trip> getByRouteAndDate(String start, String end, Date date) throws DbException;

    Map<Integer, Settlement> tripContainsSettlements(int id) throws DbException;

    void createTrip(Trip t, int startId, int endId, int[] allSettlementsId) throws DbException;

    void deleteTrip(Trip t) throws DbException;

    void deleteAllOutdatedTrips() throws DbException;

    void updateTripParameters(Trip t) throws DbException;

    void updateAllTripInfo(Trip t, int[] stations) throws DbException;

    List<Trip> userHasTrips(User user, int start, int amount) throws DbException; //, int start, int end

    int userHasTripsAmount(User u) throws DbException;
}
