package data_validation_test;
/*
  User: admin
  Cur_date: 09.09.2022
  Cur_time: 16:37
*/

import data_validation.SettlementDataValidation;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SettlementDataValidationTest {

    private final SettlementDataValidation settlementValidation;

    public SettlementDataValidationTest() {
        settlementValidation = SettlementDataValidation.getInstance();
    }

    @Test
    void checkSettlementNameTest1() {
        String settlementName = "Cherkasy";
        SettlementDataValidation settlementValidation = mock(SettlementDataValidation.class);

        settlementValidation.checkSettlementName(settlementName);

        verify(settlementValidation, times(1)).checkSettlementName(settlementName);
    }

    @Test
    void checkSettlementNameTest2() {
        String settlementName = "Kryviy Rih";
        SettlementDataValidation settlementValidation = mock(SettlementDataValidation.class);

        settlementValidation.checkSettlementName(settlementName);

        verify(settlementValidation, times(1)).checkSettlementName(settlementName);
    }

    @Test
    void checkSettlementNameTestThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> settlementValidation.checkSettlementName(""));
        assertThrows(IllegalArgumentException.class, () -> settlementValidation.checkSettlementName("       "));
        assertThrows(IllegalArgumentException.class,
                () -> settlementValidation.checkSettlementName("Llanfairpwllgwyngyll Llanfairpwllgwyngyll Llanfairpwllgwyngyll"));
        assertThrows(IllegalArgumentException.class, () -> settlementValidation.checkSettlementName("Ch1na"));
        assertThrows(IllegalArgumentException.class, () -> settlementValidation.checkSettlementName(null));
    }
}
