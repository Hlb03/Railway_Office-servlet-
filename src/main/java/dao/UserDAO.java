package dao;

import entity.User;
import exception.FailedInsertException;
import exception.FailedUpdateException;

import java.math.BigDecimal;
import java.util.List;

public interface UserDAO {

    List<User> getAllUsers() throws DbException;

    //make it Optional<User> is needed
    User getUserByLogin(String login) throws DbException;

    void insertNewUser(User u) throws DbException, FailedInsertException;

    void deleteUser(User u) throws DbException;

    void updateBalance(int id, BigDecimal balance) throws DbException, FailedUpdateException;
}
