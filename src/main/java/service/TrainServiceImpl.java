package service;
/*
  User: admin
  Cur_date: 15.08.2022
  Cur_time: 13:46
*/

import dao.DbException;
import dao.TrainDAO;
import entity.Train;

import java.util.List;

public class TrainServiceImpl implements TrainService {

    private final TrainDAO dao;

    public TrainServiceImpl(TrainDAO dao){
        this.dao = dao;
    }

    @Override
    public List<Train> getTrains() throws DbException {
        return dao.getAllTrains();
    }

    @Override
    public Train getById(String id) throws DbException {
        return dao.getTrainById(id);
    }

    @Override
    public Train getByNumber(String number) throws DbException {
        return dao.getTrainByNumber(number);
    }

    @Override
    public void createTrain(Train t) throws DbException {
        dao.createTrain(t);
    }

    @Override
    public void deleteTrain(Train t) throws DbException {
        dao.deleteTrain(t);
    }
}
