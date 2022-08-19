package entity;
/*
  User: admin
  Cur_date: 15.08.2022
  Cur_time: 12:50
*/

public class Train {

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

}
