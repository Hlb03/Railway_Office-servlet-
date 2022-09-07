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
        if (!userLogin.matches("^[A-Za-z\\d._-]+@[a-z]+[.][a-z]+$")) {
            LOG.debug("Data validation for taking user by login was failed");
            throw new IllegalArgumentException("Illegal arguments to find user");
        }
    }

    public void checkDataForCreatingNewUser(User user) {
        if (!user.getLogin().matches("^[A-Za-z\\d._-]+@[a-z]+[.][a-z]+$") ||
                user.getPassword().length() < 4 || user.getPassword().length() > 30 ||
                !user.getFirstName().matches("[A-ZА-Яa-zа-я]{2,20}") ||
                !user.getLastName().matches("[A-Za-zА-Яа-я]{1,25}")) {

            LOG.debug("Data validation for creating new user was failed");
            throw new IllegalArgumentException("Illegal arguments to create new user");
        }
    }

    public void checkDataForDeletingUser(User user) {
        if (user.getId() < 0) {
            LOG.debug("Data validation for deleting user was fatal");
            throw new IllegalArgumentException("Illegal arguments to delete user");
        }
    }

    public void checkDataForUpdatingUsersBalance(int id, BigDecimal balance) {
        if (id < 0 || balance.compareTo(new BigDecimal("200.00")) < 0) {
            LOG.debug("Data validation for updating user balance was unaccomplished");
            throw new IllegalArgumentException("Illegal arguments to update users balance");
        }
    }

    public void checkDataForUserBuyTicket(int userId, int ticketId, int amount, BigDecimal ticketPrice) {
        if (userId < 0 || ticketId < 0 || amount < 1 || ticketPrice.compareTo(new BigDecimal("180")) < 0) {
            LOG.debug("Data validation for user buy ticket was fatal");
            throw new IllegalArgumentException("Illegal arguments to buy ticket");
        }
    }

    public void checkDataForGettingAmountOfUsersTrip(User user) {
        if (user.getId() < 0) {
            LOG.debug("Data validation for getting amount of users bought trips");
            throw new IllegalArgumentException("Illegal arguments to get users trips amount");
        }
    }
}
