package dao;

import entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO<T> {

    List<User> getAllUsers();

    //make it Optional<User> is needed
    User getUserByLogin(String login);

    void insertNewUser(User u);

    void deleteUser(User u);
}
