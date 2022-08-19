package dao;
/*
  User: admin
  Cur_date: 07.08.2022
  Cur_time: 16:40
*/

import entity.Role;
import entity.User;

import javax.sql.DataSource;
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
            throw new DbException("Error while getting all users", ex);
        }


        return allUsers;
    }

    @Override
    public User getUserByLogin(String login) throws DbException {
        final String GET_USER_BY_NAME = "SELECT user.`id`, `login`, `first_name`, `last_name`, `password`, `role` " +
                "FROM `user` " +
                "  INNER JOIN `role` ON `role_id` = role.`id` AND login = ?";

        User u = null;

        try (
                Connection con = ds.getConnection();
                PreparedStatement pStatement = con.prepareStatement(GET_USER_BY_NAME)
            ){

            pStatement.setString(1, login);

            //GET IT INTO SEPARATE BLOCK 'finally'
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
                     u.setRole(new Role(rs.getString("role")));
                 }
             }

        } catch (SQLException ex){
//            System.out.println("Exception while operation");
            throw new DbException("Can't find such user", ex);
        }

        return u;
    }

    @Override
    public void insertNewUser(User u) throws DbException{
        final String INSERT_NEW_USER = "INSERT INTO `user` (`login`, `password`, `first_name`, `last_name`, `role_id`) " +
                "VALUE (?, ?, ?, ?, 4)";

        try (
                Connection con = ds.getConnection();
                PreparedStatement pStatement = con.prepareStatement(INSERT_NEW_USER)
            ){

            //con.setAutocommit(false);

            pStatement.setString(1, u.getLogin());
            pStatement.setString(2, u.getPassword());
            pStatement.setString(3, u.getFirstName());
            pStatement.setString(4, u.getLastName());

            int created = pStatement.executeUpdate();

            if (created > 0)
                System.out.println("New user was inserted with params\n" + u);

            //set con.commit();

        } catch (SQLException ex){
//            ex.printStackTrace();

            throw new DbException("Can't insert new user", ex);
        }
    }

    @Override
    public void deleteUser(User u) {}
}
