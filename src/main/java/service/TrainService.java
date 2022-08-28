package service;

import dao.DbException;
import entity.Train;

import java.util.List;

public interface TrainService {
    List<Train> getTrains() throws DbException;

    Train getById(String id) throws DbException;

    Train getByNumber(String number) throws DbException;

    void createTrain(Train t) throws DbException;

    void deleteTrain(Train t) throws DbException;
}
