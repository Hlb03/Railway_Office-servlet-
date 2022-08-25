package entity;
/*
  User: admin
  Cur_date: 15.08.2022
  Cur_time: 19:39
*/

import java.math.BigDecimal;
import java.sql.Time;
import java.util.Date;

public class Trip {

    private int id;
    private String startStation;
    private Date departureDate;
    private Time departureTime;
    private String finalStation;
    private Date arrivalDate;
    private Time arrivalTime;
    private Time duration;
    private int seats;
    private BigDecimal cost;
    private Train train;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStartStation() {
        return startStation;
    }

    public void setStartStation(String startStation) {
        this.startStation = startStation;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Time getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Time departureTime) {
        this.departureTime = departureTime;
    }

    public String getFinalStation() {
        return finalStation;
    }

    public void setFinalStation(String finalStation) {
        this.finalStation = finalStation;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Time getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Time arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Time getDuration() {
        return duration;
    }

    public void setDuration(Time duration) {
        this.duration = duration;
    }

    public int getSeats(){
        return seats;
    }

    public void setSeats(int seats){
        this.seats = seats;
    }

    public BigDecimal getCost(){
        return cost;
    }

    public void setCost(BigDecimal cost){
        this.cost = cost;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    @Override
    public String toString(){
        return id + " " + startStation + " " + departureDate + " " + departureTime + " " + finalStation + " "
                + arrivalDate + " " + arrivalTime + " " + train; //+ "(" + train.getId() + ")"
    }
}
