package service;

import dao.DbException;
import dao.UserDAO;
import entity.User;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {

    private final UserDAO dao = mock(UserDAO.class);
    private final UserServiceImpl userService = new UserServiceImpl(dao);

    @Test
    void getUsers() throws DbException {
        List<User> allUsers = Arrays.asList(
                new User(3), new User(15), new User(13)
        );

        when(userService.getUsers()).thenReturn(allUsers);
        assertEquals(allUsers, userService.getUsers());
    }

    @Test
    void getByLogin() throws DbException {
        User user = new User(14);
        user.setLogin("testLogin@gmail.com");

        when(userService.getByLogin(user.getLogin())).thenReturn(user);
        assertEquals(user, userService.getByLogin(user.getLogin()));
    }

    @Test
    void insertNewUser() throws DbException {
        User user = new User("testlog@test.ua", "testPass", "Name", "Surname");

        userService.insertNewUser(user);
        verify(dao, times(1)).insertNewUser(user);
    }

    @Test
    void deleteUser() throws DbException {
        User user = new User(13);

        userService.deleteUser(user);
        verify(dao, times(1)).deleteUser(user);
    }

    @Test
    void updateBalance() throws DbException {
        int userId = 55;
        BigDecimal newBalance = BigDecimal.valueOf(555.51);

        userService.updateBalance(userId, newBalance);
        verify(dao, times(1)).updateBalance(userId, newBalance);
    }

    @Test
    void userBuyTicket() throws DbException {
        int userId = 1;
        BigDecimal ticketPrice = BigDecimal.valueOf(201.09);

        userService.userBuyTicket(userId, 13, 2, ticketPrice);
        verify(dao, times(1)).userBuyTrip(userId, 13, 2, ticketPrice);
    }

    @Test
    void userBuyTripIfAlreadyPresent() throws DbException {
        int userId = 9;
        BigDecimal ticketPrice = BigDecimal.valueOf(303.39);

        userService.userBuyTripIfAlreadyPresent(userId, 8, 1, ticketPrice);
        verify(dao, times(1)).userBuyTripIfAlreadyPresent(userId, 8, 1, ticketPrice);
    }

    @Test
    void totalAmountOfUserTrips() throws DbException {
        User user = new User(3);
        int userTripsAmount = 6;

        when(userService.totalAmountOfUserTrips(user)).thenReturn(userTripsAmount);
        assertEquals(userTripsAmount, userService.totalAmountOfUserTrips(user));
    }
}