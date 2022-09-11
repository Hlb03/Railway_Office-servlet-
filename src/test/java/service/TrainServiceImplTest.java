package service;

import dao.DbException;
import dao.TrainDAO;
import entity.Train;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class TrainServiceImplTest {

    private final TrainDAO dao = mock(TrainDAO.class);
    private final TrainServiceImpl trainService = new TrainServiceImpl(dao);

    @Test
    void getTrains() throws DbException {
        List<Train> allTrains = Arrays.asList(
            new Train("055A"), new Train("097K"), new Train("141Д")
        );

        when(dao.getAllTrains()).thenReturn(allTrains);
        assertEquals(allTrains, trainService.getTrains());
    }

    @Test
    void getById() throws DbException {
        Train train = new Train(15);

        when(dao.getTrainById(String.valueOf(train.getId()))).thenReturn(train);
        assertEquals(train, trainService.getById(String.valueOf(train.getId())));
    }

    @Test
    void getByNumber() throws DbException {
        Train train = new Train("053A");

        when(dao.getTrainByNumber(train.getNumber())).thenReturn(train);
        assertEquals(train, dao.getTrainByNumber(train.getNumber()));
    }

    @Test
    void createTrain() throws DbException {
        Train train = new Train("088Д");

        trainService.createTrain(train.getNumber());
        verify(dao, times(1)).createTrain(train.getNumber());
    }

    @Test
    void deleteTrain() throws DbException {
        Train train = new Train("099Р");

        trainService.deleteTrain(train.getNumber());
        verify(dao, times(1)).deleteTrain(train.getNumber());
    }
}