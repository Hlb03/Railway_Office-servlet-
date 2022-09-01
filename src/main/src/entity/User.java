package entity;
/*
  User: admin
  Cur_date: 07.08.2022
  Cur_time: 16:32
*/

import java.io.Serializable;
import java.math.BigDecimal;

public class User implements Serializable {

    public User(){}

    public User(int id){
        this.id = id;
    }

    public User(String login, String password, String firstName, String lastName){
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    private int id;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private BigDecimal balance;
    private Role role;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public BigDecimal getBalance(){ return balance; }

    public void setBalance(BigDecimal balance){ this.balance = balance;}
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User params are: " + id + " " + login + " " + password + " " + firstName + " " +
                lastName + " " + balance + " " + role;
    }
}
