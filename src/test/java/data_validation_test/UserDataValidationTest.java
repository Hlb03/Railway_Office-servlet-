package data_validation_test;
/*
  User: admin
  Cur_date: 09.09.2022
  Cur_time: 22:13
*/

import data_validation.UserDataValidation;
import entity.User;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UserDataValidationTest {

    private final UserDataValidation userValidation;
    private final UserDataValidation validation = mock(UserDataValidation.class);

    public UserDataValidationTest() {
        userValidation = UserDataValidation.getInstance();
    }

    @Test
    void checkDataForTakingUserByLoginTest() {
        String userLogin = "testlog@ukr.net";

        validation.checkDataForTakingUserByLogin(userLogin);

        verify(validation, times(1)).checkDataForTakingUserByLogin(userLogin);
    }

    @Test
    void checkDataForCreatingNewUserTest() {
        User user = new User("tstlogin@gmai.com", "testPASS", "Johny", "Lea");

        validation.checkDataForCreatingNewUser(user);

        verify(validation, times(1)).checkDataForCreatingNewUser(user);
    }

    @Test
    void checkDataForDeletingUserTest() {
        User user = new User(15);

        validation.checkDataForDeletingUser(user);

        verify(validation, times(1)).checkDataForDeletingUser(user);
    }

    @Test
    void checkDataForUpdatingUsersBalanceTest() {
        int id = 30;
        BigDecimal balance = new BigDecimal("250.11");

        validation.checkDataForUpdatingUsersBalance(id, balance);

        verify(validation, times(1)).checkDataForUpdatingUsersBalance(id, balance);
    }

    @Test
    void checkDataForUserBuyTicketTest() {
        int userId = 13;
        int ticketId = 15;
        int amount = 4;
        BigDecimal price = new BigDecimal("202.18");

        validation.checkDataForUserBuyTicket(userId, ticketId, amount, price);

        verify(validation, times(1)).checkDataForUserBuyTicket(userId, ticketId, amount, price);
    }

    @Test
    void checkDataForGettingAmountOfUsersTripTest() {
        User user = new User(4);

        validation.checkDataForGettingAmountOfUsersTrip(user);

        verify(validation, times(1)).checkDataForGettingAmountOfUsersTrip(user);
    }

    @Test
    void checkDataForTakingUserByLoginTestThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> userValidation.checkDataForTakingUserByLogin(null));
        assertThrows(IllegalArgumentException.class, () -> userValidation.checkDataForTakingUserByLogin("testLog"));
        assertThrows(IllegalArgumentException.class, () -> userValidation.checkDataForTakingUserByLogin("Антон@kpi.ua"));
    }

    @Test
    void checkDataForCreatingNewUserTestThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> userValidation.checkDataForCreatingNewUser(new User()));
        assertThrows(IllegalArgumentException.class,
                () -> userValidation.checkDataForCreatingNewUser(new User("", " ", " ", "")));
        assertThrows(IllegalArgumentException.class,
                () -> userValidation.checkDataForCreatingNewUser(new User("Логін", "123456", "Test", "Case")));
        assertThrows(IllegalArgumentException.class,
                () -> userValidation.checkDataForCreatingNewUser(new User("logUA@urk.net", "qwe", "Julia", "Ty")));
    }

    @Test
    void checkDataForDeletingUserTestThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> userValidation.checkDataForCreatingNewUser(new User()));
        assertThrows(IllegalArgumentException.class, () -> userValidation.checkDataForCreatingNewUser(new User(0)));
    }

    @Test
    void checkDataForUpdatingUsersBalanceTestThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> userValidation.checkDataForUpdatingUsersBalance(0, new BigDecimal("212.11")));
        assertThrows(IllegalArgumentException.class, () -> userValidation.checkDataForUpdatingUsersBalance(12, new BigDecimal("100")));
        assertThrows(IllegalArgumentException.class, () -> userValidation.checkDataForUpdatingUsersBalance(3, null));
    }

    @Test
    void checkDataForUserBuyTicketTestThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> userValidation.checkDataForUserBuyTicket(0, 0, 0, new BigDecimal("230")));
        assertThrows(IllegalArgumentException.class, () -> userValidation.checkDataForUserBuyTicket(15, 0, 2, new BigDecimal("211")));
        assertThrows(IllegalArgumentException.class, () -> userValidation.checkDataForUserBuyTicket(3, 12, 0, new BigDecimal("211")));
        assertThrows(IllegalArgumentException.class, () -> userValidation.checkDataForUserBuyTicket(3, 12, 0, null));
        assertThrows(IllegalArgumentException.class, () -> userValidation.checkDataForUserBuyTicket(3, 12, 0, new BigDecimal("176.77")));
    }

    @Test
    void checkDataForGettingAmountOfUsersTripTestThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> userValidation.checkDataForGettingAmountOfUsersTrip(new User()));
        assertThrows(IllegalArgumentException.class, () -> userValidation.checkDataForGettingAmountOfUsersTrip(new User(0)));
    }
}
