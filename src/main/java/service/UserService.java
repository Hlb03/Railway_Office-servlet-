package service;

import dao.DbException;
import entity.User;

import java.util.List;

public interface UserService {
    List<User> getUsers() throws DbException;

    User getByLogin(String login) throws DbException;

    void insertNewUser(User user) throws DbException;

    void deleteUser(User user) throws DbException;
}
