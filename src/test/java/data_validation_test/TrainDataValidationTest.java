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
        String number = "155K";

        validation.checkTrainCreationData(number);

        verify(validation, times(1)).checkTrainCreationData(number);
    }

    @Test
    void checkTrainCreationDateTest2() {
        String number = "044A";

        validation.checkTrainCreationData(number);

        verify(validation, times(1)).checkTrainCreationData(number);
    }

    @Test
    void checkDeleteTrainDataTest1() {
        String number = "713P";

        validation.checkDeleteTrainData(number);

        verify(validation, times(1)).checkDeleteTrainData(number);
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
        assertThrows(IllegalArgumentException.class, () -> trainValidation.checkDeleteTrainData(" "));
        assertThrows(IllegalArgumentException.class, () -> trainValidation.checkTrainCreationData(":)"));
        assertThrows(IllegalArgumentException.class, () -> trainValidation.checkTrainCreationData("123"));
        assertThrows(IllegalArgumentException.class, () -> trainValidation.checkTrainCreationData("Train"));
        assertThrows(IllegalArgumentException.class, () -> trainValidation.checkTrainCreationData("155K"));
        assertThrows(IllegalArgumentException.class, () -> trainValidation.checkTrainCreationData(null));
    }

    @Test
    void checkDeleteTrainDataThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> trainValidation.checkDeleteTrainData(" "));
        assertThrows(IllegalArgumentException.class, () -> trainValidation.checkDeleteTrainData("No"));
        assertThrows(IllegalArgumentException.class, () -> trainValidation.checkDeleteTrainData("915"));
        assertThrows(IllegalArgumentException.class, () -> trainValidation.checkDeleteTrainData("153O"));
        assertThrows(IllegalArgumentException.class, () -> trainValidation.checkDeleteTrainData(null));
    }

}
