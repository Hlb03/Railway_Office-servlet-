package data_validation_test;
/*
  User: admin
  Cur_date: 09.09.2022
  Cur_time: 19:30
*/

import data_validation.TrainDataValidation;
import entity.Train;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class TrainDataValidationTest {

    private final TrainDataValidation trainValidation;
    private final TrainDataValidation validation = mock(TrainDataValidation.class);


    public TrainDataValidationTest() {
        trainValidation = TrainDataValidation.getInstance();
    }

    @Test
    void checkTrainSearchByIdTest1() {
        String id = "5";

        validation.checkTrainSearchByIdData(id);

        verify(validation, times(1)).checkTrainSearchByIdData(id);
    }

    @Test
    void checkTrainSearchByIdTest2() {
        String id = "100";

        validation.checkTrainSearchByIdData(id);

        verify(validation, times(1)).checkTrainSearchByIdData(id);
    }

    @Test
    void checkTrainSearchByName1() {
        String name = "001А";

        validation.checkTrainSearchByNameData(name);

        verify(validation, times(1)).checkTrainSearchByNameData(name);
    }

    @Test
    void checkTrainSearchByName2() {
        String name = "017Р";

        validation.checkTrainSearchByNameData(name);

        verify(validation, times(1)).checkTrainSearchByNameData(name);
    }

    @Test
    void checkTrainCreationDateTest1() {
        Train train = new Train("155K");

        validation.checkTrainCreationData(train);

        verify(validation, times(1)).checkTrainCreationData(train);
    }

    @Test
    void checkTrainCreationDateTest2() {
        Train train = new Train("044A");

        validation.checkTrainCreationData(train);

        verify(validation, times(1)).checkTrainCreationData(train);
    }

    @Test
    void checkDeleteTrainDataTest1() {
        Train train = new Train("713P");

        validation.checkDeleteTrainData(train);

        verify(validation, times(1)).checkDeleteTrainData(train);
    }

    @Test
    void checkTrainSearchByIdDataTestThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> trainValidation.checkTrainSearchByIdData("-1"));
        assertThrows(IllegalArgumentException.class, () -> trainValidation.checkTrainSearchByIdData("001F"));
        assertThrows(IllegalArgumentException.class, () -> trainValidation.checkTrainSearchByIdData(" "));
        assertThrows(IllegalArgumentException.class, () -> trainValidation.checkTrainSearchByIdData("/"));
        assertThrows(IllegalArgumentException.class, () -> trainValidation.checkTrainSearchByIdData(null));
    }

    @Test
    void checkTrainSearchByNameDataTestThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> trainValidation.checkTrainSearchByNameData(""));
        assertThrows(IllegalArgumentException.class, () -> trainValidation.checkTrainSearchByNameData("1"));
        assertThrows(IllegalArgumentException.class, () -> trainValidation.checkTrainSearchByNameData("_"));
        assertThrows(IllegalArgumentException.class, () -> trainValidation.checkTrainSearchByNameData("Good"));
        assertThrows(IllegalArgumentException.class, () -> trainValidation.checkTrainSearchByNameData("001Q"));
        assertThrows(IllegalArgumentException.class, () -> trainValidation.checkTrainSearchByNameData(null));
    }

    @Test
    void checkTrainCreationDataThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> trainValidation.checkTrainCreationData(new Train("")));
        assertThrows(IllegalArgumentException.class, () -> trainValidation.checkTrainCreationData(new Train(":)")));
        assertThrows(IllegalArgumentException.class, () -> trainValidation.checkTrainCreationData(new Train("123")));
        assertThrows(IllegalArgumentException.class, () -> trainValidation.checkTrainCreationData(new Train("Train")));
        assertThrows(IllegalArgumentException.class, () -> trainValidation.checkTrainCreationData(new Train("155K")));
        assertThrows(IllegalArgumentException.class, () -> trainValidation.checkTrainCreationData(new Train(null)));
    }

    @Test
    void checkDeleteTrainDataThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> trainValidation.checkDeleteTrainData(new Train(" ")));
        assertThrows(IllegalArgumentException.class, () -> trainValidation.checkDeleteTrainData(new Train("No")));
        assertThrows(IllegalArgumentException.class, () -> trainValidation.checkDeleteTrainData(new Train("915")));
        assertThrows(IllegalArgumentException.class, () -> trainValidation.checkDeleteTrainData(new Train("153O")));
        assertThrows(IllegalArgumentException.class, () -> trainValidation.checkDeleteTrainData(new Train(null)));
    }

}
