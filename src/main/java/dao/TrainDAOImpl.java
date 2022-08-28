package dao;
/*
  User: admin
  Cur_date: 15.08.2022
  Cur_time: 13:07
*/

import entity.Train;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrainDAOImpl implements TrainDAO {

    private final DataSource ds;

    public TrainDAOImpl(DataSource ds){
        this.ds = ds;
    }

    @Override
    public List<Train> getAllTrains() throws DbException {
        final String GET_ALL_TRAINS = "SELECT * FROM `train`";

        List<Train> allTrains = new ArrayList<>();

        try (
                Connection con = ds.getConnection();
                Statement statement = con.createStatement();
                ResultSet rs = statement.executeQuery(GET_ALL_TRAINS) ) {

            while (rs.next()){
                Train t = new Train();

                t.setId(rs.getInt(1));
                t.setNumber(rs.getString("number"));
                allTrains.add(t);
            }

        } catch (SQLException ex){
            ex.printStackTrace();
            throw new DbException("Failed to take all trains ", ex);
        }

        return allTrains;
    }

    @Override
    public Train getTrainById(String id) throws DbException {
        final String GET_TRAIN_BY_ID = "SELECT * FROM `train` WHERE id = ?";

        return getTrain(id, GET_TRAIN_BY_ID);
    }

    @Override
    public Train getTrainByNumber(String number) throws DbException {
        final String GET_TRAIN_BY_NUMBER = "SELECT * FROM `train` WHERE number = ?";

        return getTrain(number, GET_TRAIN_BY_NUMBER);
    }

    private Train getTrain(String number, String SQLQuerry) throws DbException {
        Train t = new Train();

        try (
                Connection con = ds.getConnection();
                PreparedStatement pStatement = con.prepareStatement(SQLQuerry)
        ) {

            pStatement.setString(1, number);

            //GET IT INTO SEPARATE BLOCK 'finally'
            try (
                    ResultSet rs = pStatement.executeQuery()
            ) {
                while (rs.next()){
                    t.setId(rs.getInt(1));
                    t.setNumber(rs.getString("number"));
                }
            }

        } catch (SQLException ex){
            throw new DbException("Failed to find train", ex);
        }

        return t;
    }

    @Override
    public void createTrain(Train t) throws DbException {
        final String INSERT_NEW_TRAIN = "INSERT INTO `train` (`number`) VALUE (?)";

        try (
                Connection con = ds.getConnection();
                PreparedStatement pStatement = con.prepareStatement(INSERT_NEW_TRAIN);
            ){

            pStatement.setString(1, t.getNumber());
            pStatement.executeUpdate();

        } catch (SQLException ex){
            throw new DbException("Failed to insert new train");
        }
    }

    @Override
    public void deleteTrain(Train t) throws DbException{
        final String DELETE_TRAIN = "DELETE FROM `train` WHERE `number` = ?";

        try (
                Connection con = ds.getConnection();
                PreparedStatement pStatement = con.prepareStatement(DELETE_TRAIN)
            ){
            pStatement.setString(1, t.getNumber());

            pStatement.executeUpdate();
        } catch (SQLException ex){
            throw new DbException("Failed to delete user");
        }
    }
}
