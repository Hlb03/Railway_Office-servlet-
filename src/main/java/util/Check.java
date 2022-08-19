package util;
/*
  User: admin
  Cur_date: 08.08.2022
  Cur_time: 11:40
*/

import entity.User;
import web.exception.PasswordException;
import web.exception.UserExistsException;
import web.exception.UserNotExistsException;

public class UserCheck {

    public static void ifAlreadyExists(User u) throws UserExistsException {
        if (u != null)
            throw new UserExistsException();
    }

    public static void ifNotExists(User u) throws UserNotExistsException {
        if (u == null)
            throw new UserNotExistsException();
    }

    public static void ifPasswordsSame(String pass1, String pass2) throws PasswordException {
        if (!pass1.equals(pass2))
            throw  new PasswordException();
    }
}
