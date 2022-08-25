package exception;
/*
  User: admin
  Cur_date: 24.08.2022
  Cur_time: 16:23
*/

public class FailedDeleteException extends Exception {
    public FailedDeleteException(String message){
        super(message);
    }

    public FailedDeleteException(String message, Throwable cause){
        super(message, cause);
    }
}
