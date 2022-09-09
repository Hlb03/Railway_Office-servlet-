package entity;
/*
  User: admin
  Cur_date: 15.08.2022
  Cur_time: 12:50
*/

import java.io.Serializable;
import java.util.Objects;

public class Train implements Serializable {

    private int id;
    private String number;
    public Train(){}

    public Train(int id){
        this.id = id;
    }

    public Train(String number){
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString(){return number; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Train train = (Train) o;
        return Objects.equals(number, train.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}
