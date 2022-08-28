package exception;
/*
  User: admin
  Cur_date: 15.08.2022
  Cur_time: 12:20
*/

public class EntityNotExistsException extends Exception {
    public EntityNotExistsException() {
        super();
    }

    public EntityNotExistsException(String message) {
        super(message);
    }

    public EntityNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityNotExistsException(Throwable cause) {
        super(cause);
    }

    public EntityNotExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
