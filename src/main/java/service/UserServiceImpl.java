package service;
/*
  User: admin
  Cur_date: 12.08.2022
  Cur_time: 13:40
*/

import dao.DbException;
import dao.UserDAO;
import entity.User;

import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserDAO dao;

    public UserServiceImpl(UserDAO dao){
        this.dao = dao;
    }

    @Override
    public List<User> getUsers() throws DbException {
        return dao.getAllUsers();
    }

    @Override
    public User getByLogin(String login) throws DbException {
        return dao.getUserByLogin(login);
    }

    @Override
    public void insertNewUser(User user) throws DbException {
        dao.insertNewUser(user);
    }

    @Override
    public void deleteUser(User user) throws DbException {
        dao.deleteUser(user);
    }
}
