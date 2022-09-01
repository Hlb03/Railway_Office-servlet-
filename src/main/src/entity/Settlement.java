package entity;
/*
  User: admin
  Cur_date: 15.08.2022
  Cur_time: 19:45
*/

import java.io.Serializable;
import java.util.Objects;

public class Settlement implements Serializable {

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Settlement that = (Settlement) o;
        return id == that.id && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString(){
        return name;
    }
}
