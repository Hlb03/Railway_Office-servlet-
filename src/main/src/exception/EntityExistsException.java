package exception;
/*
  User: admin
  Cur_date: 15.08.2022
  Cur_time: 12:07
*/

public class EntityExistsException extends Exception {
    public EntityExistsException() {
        super();
    }

    public EntityExistsException(String message) {
        super(message);
    }

    public EntityExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityExistsException(Throwable cause) {
        super(cause);
    }

    public EntityExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
