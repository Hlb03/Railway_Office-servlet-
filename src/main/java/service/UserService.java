package service;

import dao.DbException;
import entity.User;

import java.math.BigDecimal;
import java.util.List;

public interface UserService {
    List<User> getUsers() throws DbException;

    User getByLogin(String login) throws DbException;

    void insertNewUser(User user) throws DbException;

    void deleteUser(User user) throws DbException;

    void updateBalance(int id, BigDecimal balance) throws DbException;

    void userBuyTicket(int userId, int ticketId, int amount, BigDecimal ticketPrice) throws DbException;

    void userBuyTripIfAlreadyPresent(int userId, int ticketId, int amount, BigDecimal ticketPrice) throws DbException;

    int totalAmountOfUserTrips(User u) throws DbException;

//    List<Integer> getUserTripsId(User u) throws DbException;
}
