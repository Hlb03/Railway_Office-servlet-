package dao;
/*
  User: admin
  Cur_date: 15.08.2022
  Cur_time: 19:51
*/

import entity.Settlement;
import entity.Train;
import entity.Trip;
import entity.User;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

public class TripDAOImpl implements TripDAO{

    private final DataSource ds;

    public TripDAOImpl(DataSource ds){
        this.ds = ds;
    }

    @Override
    public List<Trip> getAllTrips(int start, int amount) throws DbException {
        final String GET_ALL_TRIPS = "SELECT trip.`id`, `start_station`, `departure`, `final_station`," +
                "       `arrival`, `duration`, `seats`, `cost`, `number`" +
                "  FROM `trip`" +
                "    INNER JOIN `train` ON trip.`train_id` = train.`id` ORDER BY 3, 5  LIMIT ?, ?";

        int first = start * amount - amount;

        List<Trip> allTrips = new LinkedList<>();

        try (
                Connection con = ds.getConnection();
                PreparedStatement pStatement = con.prepareStatement(GET_ALL_TRIPS)
            ){
                pStatement.setInt(1, first);
                pStatement.setInt(2, amount);

                try (ResultSet rs = pStatement.executeQuery()){


                while (rs.next()) {
                    Trip trip = returnTrip(rs, "seats");

                    allTrips.add(trip);
                }
            }

        } catch (SQLException ex){
            //LOG
            System.out.println("Get trips with limit is failed");
            throw new DbException("Failed to get all trips", ex);
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
    public Trip getTrip(int id, String start, String depart, String end) throws DbException {
        final String GET_TRIP = "SELECT `trip`.`id`, `start_station`, `departure`, `final_station`, `arrival`, " +
                " `duration`, `seats`, `cost`, `number` FROM `trip` INNER JOIN `train` ON `train`.`id` = `trip`.`train_id` WHERE `trip`.`id` = ? AND `start_station` = ? AND `departure` = ? AND`final_station` = ?";

        Trip t = null;

        try (
                Connection con = ds.getConnection();
                PreparedStatement pStatement = con.prepareStatement(GET_TRIP)
            ){

            pStatement.setInt(1, id);
            pStatement.setString(2, start);
            pStatement.setString(3, depart);
            pStatement.setString(4, end);

            ResultSet rs = pStatement.executeQuery();

            while (rs.next()){
                t = returnTrip(rs, "seats");
            }

        } catch (SQLException ex){
            ex.printStackTrace();
            throw new DbException("Failed to get trip", ex);
        }

        return t;
    }

    @Override
    public List<Trip> getByRoute(String start, String end) throws DbException {
        final String GET_BY_ROUTE = "SELECT trip.`id`, `start_station`, `departure`, `final_station`," +
        "       `arrival`, `duration`, `seats`, `cost`, `number`" +
                "  FROM `trip`" +
                "    INNER JOIN `train` ON trip.`train_id` = train.`id` " +
                "AND `start_station` = ? AND `final_station` = ? " +
                "ORDER BY 3"; //  LIMIT ?, ?

        List<Trip> tripsByRoute = new LinkedList<>();

        try (
                Connection con = ds.getConnection();
                PreparedStatement pStatement = con.prepareStatement(GET_BY_ROUTE)
            ){

            pStatement.setString(1, start);
            pStatement.setString(2, end);

            try (
                    ResultSet rs = pStatement.executeQuery()
                ){

                while (rs.next()) {
                    Trip t = returnTrip(rs, "seats");

                    tripsByRoute.add(t);
                }
            }

        }catch (SQLException ex){
            //LOG
            throw new DbException("Failed to get trip by route", ex);
        }

        return tripsByRoute;
    }

    @Override
    public Trip getByRouteAndDate(String start, String end, java.sql.Date date) throws DbException {
        return null;
    }

    @Override
    public void createTrip(Trip trip, int startSettlementId, int endSettlementId, Integer[] allSettlementsId)
            throws DbException {
        //Insert into `trip table`
        final String CREATE_NEW_TRIP = "INSERT INTO `trip` (`start_station`, `departure`, `final_station`, `arrival`," +
                                                        " `duration`, `seats`, `cost`, train_id)" +
                "                           VALUES ((SELECT `name` FROM `settlement` WHERE `id` = ?)," +
                                                  " ?," +
                               "                    (SELECT `name` FROM `settlement` WHERE `id` = ?)," +
                                "                   ?, TIMEDIFF(`arrival`, `departure`), ?, ?, ?)";

        Connection con = null;
        PreparedStatement pStatement = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        try {

            con = ds.getConnection();
            con.setAutoCommit(false);

            pStatement = con.prepareStatement(CREATE_NEW_TRIP, Statement.RETURN_GENERATED_KEYS);



            pStatement.setInt(1, startSettlementId);
            pStatement.setObject(2, trip.getDepartureDate() + " " + trip.getDepartureTime());
            pStatement.setInt(3, endSettlementId);
            pStatement.setObject(4, trip.getArrivalDate() + " " + trip.getArrivalTime());
            pStatement.setInt(5, trip.getSeats());
            pStatement.setObject(6, trip.getCost());
            pStatement.setInt(7, trip.getTrain().getId());

            if (pStatement.executeUpdate() == 0)
                throw new DbException("Failed to insert trip"); //create and throw new own exception

            int newTripId = -1;

            rs = pStatement.getGeneratedKeys();

            while (rs.next())
                newTripId = rs.getInt(1);


            //Insert into `trip_has_settlement` table
            StringBuilder INSERT_NEW_TRIP_HAS_SETTLEMENT = new StringBuilder("INSERT INTO `trip_has_settlement` " +
                    "(`trip_id`, `settlement_id`, `order`)" +
                    " VALUES (" + newTripId + ", ?, 1)");

            for (int i = 2; i <= allSettlementsId.length; i++) {
                INSERT_NEW_TRIP_HAS_SETTLEMENT.append(", (").append(newTripId).append(", ?, ").append(i).append(")");
            }

            preparedStatement = con.prepareStatement(INSERT_NEW_TRIP_HAS_SETTLEMENT.toString());

            for (int i = 1; i <= allSettlementsId.length; i++) {
                preparedStatement.setInt(i, allSettlementsId[i-1]);
            }

            con.commit();

        } catch (SQLException ex){
            //LOG
            if (con != null) {
              try {
                  con.rollback();
                  ex.printStackTrace();
              } catch (SQLException e){
                  throw new DbException("Failed to insert new trip", ex);
              }
            }
        } finally {
            if (rs != null){
                try {
                    rs.close();
                } catch (SQLException ex){}
            }
            if (preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e){}
            }
            if (pStatement != null){
                try {
                    pStatement.close();
                } catch (SQLException exception){}
            }
            if (con != null){
                try {
                    con.close();
                } catch (SQLException exc){}
            }
        }
    }

    @Override
    public void deleteTrip(Trip trip) throws DbException {
        final String DELETE_TRIP_HAS_SETTLEMENT = "DELETE FROM `trip_has_settlement` WHERE `trip_id` = ?";
        final String DELETE_TRIP = "DELETE FROM `trip` WHERE `id` = ? AND `start_station` = ? AND `final_station` = ?";

        Connection con = null;
        try {

            con = ds.getConnection();
            con.setAutoCommit(false);

            try (
                    PreparedStatement pStatement = con.prepareStatement(DELETE_TRIP_HAS_SETTLEMENT);
                    PreparedStatement preparedStatement = con.prepareStatement(DELETE_TRIP)
                ){

                pStatement.setInt(1, trip.getId());

                preparedStatement.setInt(1, trip.getId());
                preparedStatement.setString(2, trip.getStartStation());
                preparedStatement.setString(3, trip.getFinalStation());

                preparedStatement.executeUpdate();
            }
            con.commit();

        } catch (SQLException ex){
            if (con != null){
                try {
                    con.rollback();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            throw new DbException("Fail with data base", ex);
        } finally {
            if (con != null){
                try {
                    con.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }

    public void deleteAllOutdatedTrips() throws DbException {
        final String DELETE_ALL_OUTDATED_TRIPS = "DELETE FROM `trip` WHERE `arrival` < NOW()";

        try (
                Connection con = ds.getConnection();
                Statement statement = con.createStatement()
            ){

            statement.executeUpdate(DELETE_ALL_OUTDATED_TRIPS);

        } catch (SQLException ex){
            throw new DbException("Failed to delete all outdated trips");
        }
    }

    @Override
    public Map<Integer, Settlement> getTripSettlements(int id) throws DbException {
        final String GET_TRIP_SETTLEMENTS = "SELECT `order`, `name` FROM `settlement`" +
                "INNER JOIN `trip_has_settlement` ON `settlement`.`id` = `trip_has_settlement`.`settlement_id` AND `trip_id` = ? ORDER BY 1";

        Map<Integer, Settlement> tripHasSettlements = new HashMap<>();

        try (
                Connection con = ds.getConnection();
                PreparedStatement pStatement = con.prepareStatement(GET_TRIP_SETTLEMENTS)
            ){

            pStatement.setInt(1, id);

            try (
                    ResultSet rs = pStatement.executeQuery();
                ) {

                while (rs.next()) {
                    int order = rs.getInt(1);
                    Settlement settlement = new Settlement(rs.getString(2));

                    tripHasSettlements.put(order, settlement);
                }
            }

        } catch (SQLException ex){
            //LOG
            throw  new DbException("Failed to get trip's settlements", ex);
        }

        return tripHasSettlements;
    }

    @Override
    public List<Trip> userHasTrips(User user) throws DbException{ //, int start, int amount
        final String GET_USERS_TRIPS = "SELECT `trip`.`id`, `start_station`, `departure`, `final_station`," +
                " `arrival`, `duration`, `amount`, `cost`, `number` " +
                "FROM `trip` INNER JOIN `user_has_trip` ON `trip`.`id` = `user_has_trip`.`trip_id` AND `user_id` = ? " +
                "INNER JOIN `train` ON `trip`.`train_id` = `train`.`id` "; // LIMIT ?, ?
        List<Trip> allUsersTrips = new LinkedList<>();

//        int first = start * amount - amount;

        try (
                Connection con = ds.getConnection();
                PreparedStatement pStatement = con.prepareStatement(GET_USERS_TRIPS)
        ){
            pStatement.setInt(1, user.getId());
//            pStatement.setInt(2, first);
//            pStatement.setInt(3, amount);

            try (
                    ResultSet rs = pStatement.executeQuery()
            ){
                while (rs.next()){
                    Trip t = returnTrip(rs, "amount");

                    allUsersTrips.add(t);
                }
            }

        } catch (SQLException ex){
            ex.printStackTrace();
            throw new DbException("Failed to get users trips");
        }

        return allUsersTrips;
    }


    private Trip returnTrip(ResultSet rs, String seatsName) throws SQLException{
        String[] departure = rs.getString("departure").split(" ");
        String[] arrival = rs.getString("arrival").split(" ");

        Trip trip = new Trip();
        trip.setId(rs.getInt(1));
        trip.setStartStation(rs.getString("start_station"));
        trip.setDepartureDate(java.sql.Date.valueOf(departure[0]));
        trip.setDepartureTime(java.sql.Time.valueOf(departure[1]));
        trip.setFinalStation(rs.getString("final_station"));
        trip.setArrivalDate(java.sql.Date.valueOf(arrival[0]));
        trip.setArrivalTime(java.sql.Time.valueOf(arrival[1]));
        trip.setDuration(rs.getTime("duration"));
        trip.setSeats(rs.getInt(seatsName));
        trip.setCost((BigDecimal) rs.getObject("cost"));

        trip.setTrain(new Train(rs.getString("number")));

        return trip;
    }
}
