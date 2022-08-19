package dao;
/*
  User: admin
  Cur_date: 15.08.2022
  Cur_time: 19:51
*/

import entity.Train;
import entity.Trip;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TripDAOImpl implements TripDAO{

    private final DataSource ds;

    public TripDAOImpl(DataSource ds){
        this.ds = ds;
    }

    @Override
    public List<Trip> getAllTrips(int start, int amount) throws DbException {
        final String GET_ALL_TRIPS = "SELECT trip.`id`, `start_station`, `departure_date`, `departure_time`, `final_station`," +
                "       `arrival_date`, `arrival_time`, `seats`, `cost`, `number`" +
                "  FROM `trip`" +
                "    INNER JOIN `train` ON trip.`train_id` = train.`id` ORDER BY 3  LIMIT ?, ?";

        int first = start * amount - amount;

        List<Trip> allTrips = new ArrayList<>();

        ResultSet rs = null;

        try (
                Connection con = ds.getConnection();
                PreparedStatement pStatement = con.prepareStatement(GET_ALL_TRIPS);
            ){
                pStatement.setInt(1, first);
                pStatement.setInt(2, amount);

                rs = pStatement.executeQuery();

            while (rs.next()){
                Trip trip = new Trip();
                trip.setId(rs.getInt(1));
                trip.setStartStation(rs.getString("start_station"));
                trip.setDepartureDate(rs.getDate("departure_date"));
                trip.setDepartureTime(rs.getTime("departure_time"));
                trip.setFinalStation(rs.getString("final_station"));
                trip.setArrivalDate(rs.getDate("arrival_date"));
                trip.setArrivalTime(rs.getTime("arrival_time"));
                trip.setSeats(rs.getInt("seats"));
                trip.setCost(rs.getDouble("cost"));

                trip.setTrain(new Train(rs.getString("number")));

                allTrips.add(trip);
            }

        } catch (SQLException ex){
            //LOG
            System.out.println("Get trips with limit is failed");
            throw new DbException("Failed to get all trips", ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    //LOG
                    throw new RuntimeException(e);
                }
            }
        }

        return allTrips;
    }

    @Override
    public int getTripsAmount() throws DbException {
        final String GET_TRIPS_AMOUNT = "SELECT COUNT(`id`) AS `amount`" +
                "  FROM `trip`";

        int amount = 0;

        try (
                Connection con = ds.getConnection();
                Statement statement = con.createStatement();
                ResultSet rs = statement.executeQuery(GET_TRIPS_AMOUNT)
            ){

            while (rs.next())
                amount = rs.getInt(1);

        } catch (SQLException ex){
            //LOG
            throw new DbException("Failed to get size", ex);
        }

        return amount;
    }

    @Override
    public Trip getById(int id) throws DbException {
        return null;
    }

    @Override
    public List<Trip> getByRoute(String start, String end) throws DbException {
        final String GET_BY_ROUTE = "SELECT trip.`id`, `start_station`, `departure_date`, `departure_time`, `final_station`," +
        "       `arrival_date`, `arrival_time`, `seats`, `cost`, `number`" +
                "  FROM `trip`" +
                "    INNER JOIN `train` ON trip.`train_id` = train.`id` " +
                "AND `start_station` = ? AND `final_station` = ? " +
                "ORDER BY 3"; //  LIMIT ?, ?

        List<Trip> tripsByRoute = new ArrayList<>();

        ResultSet rs = null;
        try (
                Connection con = ds.getConnection();
                PreparedStatement pStatement = con.prepareStatement(GET_BY_ROUTE)
            ){
            pStatement.setString(1, start);
            pStatement.setString(2, end);

            rs = pStatement.executeQuery();

            while (rs.next()){
                Trip t = new Trip();

                t.setId(rs.getInt(1));
                t.setStartStation(rs.getString("start_station"));
                t.setDepartureDate(rs.getDate("departure_date"));
                t.setDepartureTime(rs.getTime("departure_time"));
                t.setFinalStation(rs.getString("final_station"));
                t.setArrivalDate(rs.getDate("arrival_date"));
                t.setArrivalTime(rs.getTime("arrival_time"));
                t.setSeats(rs.getInt("seats"));
                t.setCost(rs.getDouble("cost"));

                t.setTrain(new Train(rs.getString("number")));

                tripsByRoute.add(t);
            }

        }catch (SQLException ex){
            //LOG
            throw new DbException("Failed to get trip by route", ex);
        }

        return tripsByRoute;
    }

    @Override
    public Trip getByRouteAndDate(String start, String end, java.util.Date date) throws DbException {
        return null;
    }

//    public Trip getByRouteAndDate(String start, String end, Date date) throws DbException{
//        return null;
//    }

    @Override
    public void createTrip(Trip trip) throws DbException {

    }

    @Override
    public void deleteTrip(Trip trip) throws DbException{}
}
