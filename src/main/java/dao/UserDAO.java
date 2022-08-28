package dao;

import entity.User;

import java.math.BigDecimal;
import java.util.List;

public interface UserDAO {

    List<User> getAllUsers() throws DbException;

    User getUserByLogin(String login) throws DbException;

    void insertNewUser(User u) throws DbException;

    void deleteUser(User u) throws DbException;

    void updateBalance(int id, BigDecimal balance) throws DbException;

    void userBuyTrip(int userId, int ticketId, int amount, BigDecimal ticketPrice) throws DbException;

    void userBuyTripIfAlreadyPresent(int userId, int ticketId, int amount, BigDecimal ticketPrice) throws DbException;

    int totalAmountOfUserTrips(User u) throws DbException;

//    List<Integer> getUserTripsId(User u) throws DbException;
//    List<Trip> getUserTrips(User u) throws DbException;
}
