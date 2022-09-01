package entity;
/*
  User: admin
  Cur_date: 07.08.2022
  Cur_time: 16:33
*/

import java.io.Serializable;
import java.util.Objects;

public class Role implements Serializable {
    private int id;
    private String roleName;

    //Such constructor is needed to set a role to user
    public Role(String roleName){
        this.roleName = roleName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return id == role.id && Objects.equals(roleName, role.roleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roleName);
    }

    @Override
    public String toString(){
        return roleName;
    }
}
