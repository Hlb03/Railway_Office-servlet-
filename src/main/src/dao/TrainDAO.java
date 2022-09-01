package dao;

import entity.Train;

import java.util.List;

public interface TrainDAO {

    List<Train> getAllTrains() throws DbException;

    Train getTrainById(String id) throws DbException;

    Train getTrainByNumber(String number) throws DbException;

    void createTrain(Train t) throws DbException;

    void deleteTrain(Train t) throws DbException;
}
