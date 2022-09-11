package service;

import dao.DbException;
import entity.Train;

import java.util.List;

public interface TrainService {
    List<Train> getTrains() throws DbException;

    Train getById(String id) throws DbException;

    Train getByNumber(String number) throws DbException;

    void createTrain(String number) throws DbException;

    void deleteTrain(String number) throws DbException;
}
