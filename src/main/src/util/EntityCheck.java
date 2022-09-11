package util;
/*
  User: admin
  Cur_date: 08.08.2022
  Cur_time: 11:40
*/

import entity.User;
import exception.PasswordException;
import exception.EntityExistsException;
import exception.EntityNotExistsException;

public class EntityCheck {

    public static void ifAlreadyExists(User u) throws EntityExistsException {
        if (u != null)
            throw new EntityExistsException();
    }

    public static void ifNotExists(User u) throws EntityNotExistsException {
        if (u == null)
            throw new EntityNotExistsException();
    }

    public static void ifPasswordsSame(String pass1, String pass2) throws PasswordException {
        if (pass1 == null || !pass1.equals(pass2))
            throw  new PasswordException();
    }
}
