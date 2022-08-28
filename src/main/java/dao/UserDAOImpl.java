package dao;
/*
  User: admin
  Cur_date: 07.08.2022
  Cur_time: 16:40
*/

import entity.Role;
import entity.User;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAO {

    private final DataSource ds;

    public UserDAOImpl(DataSource ds){
        this.ds = ds;
    }

    @Override
    public List<User> getAllUsers() throws DbException {
        final String GET_ALL_USERS = "SELECT * FROM `user`";

        List<User> allUsers = new ArrayList<>();


        try (
                Connection connection = ds.getConnection();
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(GET_ALL_USERS)
            ) {
            while (rs.next()){
                User u = setParams(rs);

                allUsers.add(u);
            }

        } catch (SQLException ex){
            throw new DbException("Error while getting all users", ex);
        }


        return allUsers;
    }

    @Override
    public User getUserByLogin(String login) throws DbException {
        final String GET_USER_BY_NAME = "SELECT user.`id`, `login`, `first_name`, `last_name`, `password`, `balance`, `role` " +
                "FROM `user` " +
                "  INNER JOIN `role` ON `role_id` = role.`id` AND login = ?";

        User u = null;

        try (
                Connection con = ds.getConnection();
                PreparedStatement pStatement = con.prepareStatement(GET_USER_BY_NAME)
            ){

            pStatement.setString(1, login);

            try (
                    ResultSet rs = pStatement.executeQuery()
                 ){
                 while (rs.next()){
                     u = setParams(rs);
                 }
             }

        } catch (SQLException ex){
            throw new DbException("Can't find such user", ex);
        }

        return u;
    }

    @Override
    public void insertNewUser(User u) throws DbException {
        final String INSERT_NEW_USER = "INSERT INTO `user` (`login`, `password`, `first_name`, `last_name`, `balance`, `role_id`) " +
                "VALUE (?, ?, ?, ?, 0, 4)";

        try (
                Connection con = ds.getConnection();
                PreparedStatement pStatement = con.prepareStatement(INSERT_NEW_USER)
            ){

            pStatement.setString(1, u.getLogin());
            pStatement.setString(2, u.getPassword());
            pStatement.setString(3, u.getFirstName());
            pStatement.setString(4, u.getLastName());

        } catch (SQLException ex){
            throw new DbException("Can't insert new user", ex);
        }
    }

    @Override
    public void updateBalance(int id, BigDecimal balance) throws DbException {
        final String UPDATE_USER_BALANCE = "UPDATE `user` SET `balance` = `balance` + ? WHERE `id` = ?";

        try (
                Connection con = ds.getConnection();
                PreparedStatement pStatement = con.prepareStatement(UPDATE_USER_BALANCE)
            ){

            pStatement.setBigDecimal(1, balance);
            pStatement.setInt(2, id);

            pStatement.executeUpdate();

        } catch (SQLException exception){
            throw new DbException();
        }
    }

    @Override
    public void deleteUser(User u) throws DbException {}

    //Add possibility to buy more than one same ticket, in different time
    @Override
    public void userBuyTrip(int userId, int ticketId, int amount, BigDecimal ticketPrice) throws DbException{
        final String USER_BUY_TICKET = "INSERT INTO `user_has_trip` (`user_id`, `trip_id`, `amount`) VALUES (?, ?, ?)";
        final String UPDATE_USER_BALANCE = "UPDATE `user` SET `balance` = `balance` - (? * ?) WHERE `id` = ?";
        final String UPDATE_AMOUNT_OF_TRIPS_SEATS = "UPDATE `trip` SET `seats` = `seats` - ? WHERE `id` = ?";

        Connection con = null;
        try {

            con = ds.getConnection();
            con.setAutoCommit(false);

            try (
                PreparedStatement pStatement = con.prepareStatement(USER_BUY_TICKET);
                PreparedStatement prepStatement = con.prepareStatement(UPDATE_USER_BALANCE);
                PreparedStatement preparedStatement = con.prepareStatement(UPDATE_AMOUNT_OF_TRIPS_SEATS)
            ) {

                pStatement.setInt(1, userId);
                pStatement.setInt(2, ticketId);
                pStatement.setInt(3, amount);

                prepStatement.setBigDecimal(1, ticketPrice);
                prepStatement.setDouble(2, amount);
                prepStatement.setInt(3, userId);

                preparedStatement.setInt(1, amount);
                preparedStatement.setInt(2, ticketId);

                pStatement.executeUpdate();
                prepStatement.executeUpdate();
                preparedStatement.executeUpdate();
            }

            con.commit();
        } catch (SQLException ex){
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException exception) {
                    throw new RuntimeException(exception);
                }
            }

            throw new DbException("Failed to buy ticket", ex);
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

    @Override
    public void userBuyTripIfAlreadyPresent(int userId, int ticketId, int amount, BigDecimal ticketPrice) throws DbException {
        final String UPDATE_AMOUNT_OF_BOUGHT_TICKETS = "UPDATE `user_has_trip` SET `amount` = `amount` + ? WHERE `user_id` = ?";
        final String UPDATE_USER_BALANCE = "UPDATE `user` SET `balance` = `balance` - (? * ?) WHERE `id` = ?";
        final String UPDATE_AMOUNT_OF_TRIPS_SEATS = "UPDATE `trip` SET `seats` = `seats` - ? WHERE `id` = ?";

        Connection con = null;
        try {

            con = ds.getConnection();
            con.setAutoCommit(false);

            try(
                    PreparedStatement pStatement = con.prepareStatement(UPDATE_AMOUNT_OF_BOUGHT_TICKETS);
                    PreparedStatement prepStatement = con.prepareStatement(UPDATE_USER_BALANCE);
                    PreparedStatement preparedStatement = con.prepareStatement(UPDATE_AMOUNT_OF_TRIPS_SEATS)
                ){
                pStatement.setInt(1, amount);
                pStatement.setInt(2, userId);

                prepStatement.setBigDecimal(1, ticketPrice);
                prepStatement.setInt(2, amount);
                prepStatement.setInt(3, userId);

                preparedStatement.setInt(1, amount);
                preparedStatement.setInt(2, ticketId);

                pStatement.executeUpdate();
                prepStatement.executeUpdate();
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
            throw new DbException("Failed to buy already existed ticket");
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

    @Override
    public int totalAmountOfUserTrips(User u) throws DbException {
        final String GET_SUM_OF_USER_TRIPS = "SELECT SUM(`amount`) AS `sum` FROM `user_has_trip` WHERE `user_id` = ?";
        int amount = 0;

        try (
                Connection con = ds.getConnection();
                PreparedStatement pStatement = con.prepareStatement(GET_SUM_OF_USER_TRIPS)
            ){

            pStatement.setInt(1, u.getId());

            try (ResultSet rs = pStatement.executeQuery()){
                while (rs.next())
                    amount = rs.getInt(1);
            }

        } catch (SQLException ex){
            throw new DbException("Failed to get the sum of users trips");
        }

        return amount;
    }

//    @Override
//    public List<Integer> getUserTripsId(User u) throws DbException{
//        final String GET_USERS_TRIPS = "SELECT * FROM `user_has_trip` WHERE `user_id` = ?";
//        List<Integer> allUsersTrips = new LinkedList<>();
//
//        try (
//                Connection con = ds.getConnection();
//                PreparedStatement pStatement = con.prepareStatement(GET_USERS_TRIPS)
//            ){
//            pStatement.setInt(1, u.getId());
//
//            try (
//                    ResultSet rs = pStatement.executeQuery();
//                ){
//                while (rs.next())
//                    allUsersTrips.add(rs.getInt("trip_id"));
//            }
//
//        } catch (SQLException ex){
//            throw new DbException("Failed to get users trips");
//        }
//
//        return allUsersTrips;
//    }

    private User setParams(ResultSet rs) throws SQLException{
        User u = new User();

        u.setId(rs.getInt(1));
        u.setLogin(rs.getString("login"));
        u.setPassword(rs.getString("password"));
        u.setFirstName(rs.getString("first_name"));
        u.setLastName(rs.getString("last_name"));
        u.setBalance(rs.getBigDecimal("balance"));
        u.setRole(new Role(rs.getString("role")));

        return u;
    }
}
