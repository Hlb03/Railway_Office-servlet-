package service;
/*
  User: admin
  Cur_date: 15.08.2022
  Cur_time: 13:46
*/

import dao.DbException;
import dao.TrainDAO;
import data_validation.TrainDataValidation;
import entity.Train;

import java.util.List;

public class TrainServiceImpl implements TrainService {

    private final TrainDAO dao;
    private final static TrainDataValidation VALIDATION = TrainDataValidation.getInstance();

    public TrainServiceImpl(TrainDAO dao){
        this.dao = dao;
    }

    @Override
    public List<Train> getTrains() throws DbException {
        return dao.getAllTrains();
    }

    @Override
    public Train getById(String id) throws DbException {
        VALIDATION.checkTrainSearchByIdData(id);

        return dao.getTrainById(id);
    }

    @Override
    public Train getByNumber(String number) throws DbException {
        VALIDATION.checkTrainSearchByNameData(number);

        return dao.getTrainByNumber(number);
    }

    @Override
    public void createTrain(Train t) throws DbException {
        VALIDATION.checkTrainCreationData(t);

        dao.createTrain(t);
    }

    @Override
    public void deleteTrain(Train t) throws DbException {
        VALIDATION.checkDeleteTrainData(t);

        dao.deleteTrain(t);
    }
}
