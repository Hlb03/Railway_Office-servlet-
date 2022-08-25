package entity;
/*
  User: admin
  Cur_date: 15.08.2022
  Cur_time: 19:45
*/

public class Settlement {

    public Settlement(){}
    public Settlement(String name){
        this.name = name;
    }
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString(){
        return name;
    }
}
