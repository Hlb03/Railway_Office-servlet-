package dao;
/*
  User: admin
  Cur_date: 07.08.2022
  Cur_time: 16:40
*/

import entity.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAOImpl implements UserDAO<User> {

    private final DataSource ds;

    public UserDAOImpl(DataSource ds){
        this.ds = ds;
    }

    @Override
    public List<User> getAllUsers() {
        final String GET_ALL_USERS = "SELECT * FROM `user`";

        List<User> allUsers = new ArrayList<>();


        try (
                Connection connection = ds.getConnection();
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(GET_ALL_USERS)
            ) {
            while (rs.next()){
                User u = new User();
                u.setId(rs.getInt(1));
                u.setLogin(rs.getString("login"));
                u.setPassword(rs.getString("password"));
                u.setFirstName(rs.getString("first_name"));
                u.setLastName(rs.getString("last_name"));
//                u.setRole(new Role().setId(rs.getInt(6)));

                allUsers.add(u);
            }

        } catch (SQLException ex){
            System.out.println("Failed to get resources");
            ex.printStackTrace();
        }


        return allUsers;
    }

    @Override
    public Optional<User> getUserByLogin(String login) {
        final String GET_USER_BY_NAME = "SELECT * FROM `user` WHERE login = ?";

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
                     u = new User();

                     u.setId(rs.getInt(1));
                     u.setLogin(rs.getString("login"));
                     u.setPassword(rs.getString("password"));
                     u.setFirstName(rs.getString("first_name"));
                     u.setLastName(rs.getString("last_name"));
                 }
             }

        } catch (SQLException ex){
            System.out.println("Exception while operation");
        }

        return Optional.ofNullable(u);
    }

    @Override
    public void insertNewUser(User u) {

    }

    @Override
    public void deleteUser(User u) {

    }
}
