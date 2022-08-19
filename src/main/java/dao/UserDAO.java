package dao;

import entity.User;

import java.util.List;

public interface UserDAO {

    List<User> getAllUsers() throws DbException;

    //make it Optional<User> is needed
    User getUserByLogin(String login) throws DbException;

    void insertNewUser(User u) throws DbException;

    void deleteUser(User u) throws DbException;
}
