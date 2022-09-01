package exception;
/*
  User: admin
  Cur_date: 15.08.2022
  Cur_time: 12:26
*/

public class PasswordException extends Exception {
    public PasswordException() {
        super();
    }

    public PasswordException(String message) {
        super(message);
    }

    public PasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordException(Throwable cause) {
        super(cause);
    }

    public PasswordException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
