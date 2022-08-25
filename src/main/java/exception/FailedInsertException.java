package exception;
/*
  User: admin
  Cur_date: 24.08.2022
  Cur_time: 12:51
*/

public class FailedInsertException extends Exception{
    public FailedInsertException(String message){
        super(message);
    }

    public FailedInsertException(String message, Throwable cause){
        super(message, cause);
    }
}
