package service;
/*
  User: admin
  Cur_date: 12.08.2022
  Cur_time: 13:40
*/

import dao.DbException;
import dao.UserDAO;
import data_validation.UserDataValidation;
import entity.User;

import java.math.BigDecimal;
import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserDAO dao;

    private final static UserDataValidation VALIDATION = UserDataValidation.getInstance();

    public UserServiceImpl(UserDAO dao){
        this.dao = dao;
    }

    @Override
    public List<User> getUsers() throws DbException {
        return dao.getAllUsers();
    }

    @Override
    public User getByLogin(String login) throws DbException {
        VALIDATION.checkDataForTakingUserByLogin(login);

        return dao.getUserByLogin(login);
    }

    @Override
    public void insertNewUser(User user) throws DbException {
        VALIDATION.checkDataForCreatingNewUser(user);

        dao.insertNewUser(user);
    }

    @Override
    public void deleteUser(User user) throws DbException {
        VALIDATION.checkDataForDeletingUser(user);

        dao.deleteUser(user);
    }

    @Override
    public void updateBalance(int id, BigDecimal balance) throws DbException {
        VALIDATION.checkDataForUpdatingUsersBalance(id, balance);

        dao.updateBalance(id, balance);
    }

    @Override
    public void userBuyTicket(int userId, int ticketId, int amount, BigDecimal ticketPrice) throws DbException {
        VALIDATION.checkDataForUserBuyTicket(userId, ticketId, amount, ticketPrice);

        dao.userBuyTrip(userId, ticketId, amount, ticketPrice);
    }

    @Override
    public void userBuyTripIfAlreadyPresent(int userId, int ticketId, int amount, BigDecimal ticketPrice) throws DbException{
        VALIDATION.checkDataForUserBuyTicket(userId, ticketId, amount, ticketPrice);

        dao.userBuyTripIfAlreadyPresent(userId, ticketId, amount, ticketPrice);
    }

    @Override
    public int totalAmountOfUserTrips(User u) throws DbException {
        VALIDATION.checkDataForGettingAmountOfUsersTrip(u);

        return dao.totalAmountOfUserTrips(u);
    }
}
