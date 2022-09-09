package data_validation;
/*
  User: admin
  Cur_date: 06.09.2022
  Cur_time: 21:07
*/

import entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;

public class UserDataValidation {
    private static UserDataValidation VALIDATION;

    private static final Logger LOG = LogManager.getLogger(UserDataValidation.class);

    private UserDataValidation(){}

    public static synchronized UserDataValidation getInstance(){
        if (VALIDATION == null) {
            VALIDATION = new UserDataValidation();
            LOG.trace("UserDataValidation was instantiated successfully");
        }

        return VALIDATION;
    }

    public void checkDataForTakingUserByLogin(String userLogin) {
        if (userLogin == null || !userLogin.matches("^[A-Za-z\\d._-]+@[a-z]+[.][a-z]+$")) {
            LOG.debug("Data validation for taking user by login was failed");
            throw new IllegalArgumentException("Illegal arguments to find user");
        }
    }

    public void checkDataForCreatingNewUser(User user) {
        if (user == null || user.getLogin() == null || user.getFirstName() == null || user.getLastName() == null || user.getPassword() == null ||
                user.getLogin().trim().isEmpty() || user.getFirstName().trim().isEmpty() || user.getLastName().trim().isEmpty() ||
                user.getPassword().trim().isEmpty() ||
                !user.getLogin().matches("^[A-Za-z\\d._-]+@[a-z]+[.][a-z]+$") || user.getLogin().length() > 45 ||
                user.getPassword().length() < 4 || user.getPassword().length() > 30 ||
                !user.getFirstName().matches("[A-ZА-Яa-zа-я]{2,20}") || user.getFirstName().length() > 20 ||
                !user.getLastName().matches("[A-Za-zА-Яа-я]{1,25}") || user.getLastName().length() > 25) {

            LOG.debug("Data validation for creating new user was failed");
            throw new IllegalArgumentException("Illegal arguments to create new user");
        }
    }

    public void checkDataForDeletingUser(User user) {
        if (user == null || user.getId() < 1) {
            LOG.debug("Data validation for deleting user was fatal");
            throw new IllegalArgumentException("Illegal arguments to delete user");
        }
    }

    public void checkDataForUpdatingUsersBalance(int id, BigDecimal balance) {
        if (balance == null || id < 1 || balance.compareTo(new BigDecimal("200.00")) < 0) {
            LOG.debug("Data validation for updating user balance was unaccomplished");
            throw new IllegalArgumentException("Illegal arguments to update users balance");
        }
    }

    public void checkDataForUserBuyTicket(int userId, int ticketId, int amount, BigDecimal ticketPrice) {
        if (ticketPrice == null || userId < 1 || ticketId < 1 || amount < 1 || ticketPrice.compareTo(new BigDecimal("180")) < 0) {
            LOG.debug("Data validation for user buy ticket was fatal");
            throw new IllegalArgumentException("Illegal arguments to buy ticket");
        }
    }

    public void checkDataForGettingAmountOfUsersTrip(User user) {
        if (user == null || user.getId() < 1) {
            LOG.debug("Data validation for getting amount of users bought trips");
            throw new IllegalArgumentException("Illegal arguments to get users trips amount");
        }
    }
}
