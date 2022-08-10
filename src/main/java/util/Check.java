package util;
/*
  User: admin
  Cur_date: 08.08.2022
  Cur_time: 11:40
*/

import entity.User;

public class Check {

    public static boolean ifAlreadyExists(User u){
        return u != null;
    }

    public static boolean ifPasswordsSame(String pass1, String pass2){
        return pass1.equals(pass2);
    }
}
