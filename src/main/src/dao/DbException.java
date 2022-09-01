package dao;
/*
  User: admin
  Cur_date: 12.08.2022
  Cur_time: 13:04
*/

import java.sql.SQLException;

public class DbException extends SQLException {
    public DbException(String reason, String SQLState, int vendorCode) {
        super(reason, SQLState, vendorCode);}

    public DbException(String reason, String SQLState) {
        super(reason, SQLState);
    }

    public DbException(String reason) {
        super(reason);
    }

    public DbException() { super();
    }

    public DbException(Throwable cause) {
        super(cause);
    }

    public DbException(String reason, Throwable cause) {
        super(reason, cause);
    }

    public DbException(String reason, String sqlState, Throwable cause) {
        super(reason, sqlState, cause);
    }

    public DbException(String reason, String sqlState, int vendorCode, Throwable cause) {
        super(reason, sqlState, vendorCode, cause);
    }
}
