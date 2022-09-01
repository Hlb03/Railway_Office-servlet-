package entity;
/*
  User: admin
  Cur_date: 15.08.2022
  Cur_time: 19:39
*/

import javax.sql.rowset.serial.SerialArray;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Date;
import java.util.Objects;

public class Trip implements Serializable {

    private int id;
    private String startStation;
    private Date departureDate;
    private Time departureTime;
    private String finalStation;
    private Date arrivalDate;
    private Time arrivalTime;
    private String duration;
    private int seats;
    private BigDecimal cost;
    private Train train;

    public Trip(){}

    public Trip(int id){
        this.id = id;
    }

    public Trip(Date departureDate, Time departureTime, Date arrivalDate, Time arrivalTime, int seats, BigDecimal cost, Train train){
        this.departureDate = departureDate;
        this.departureTime = departureTime;
        this.arrivalDate = arrivalDate;
        this.arrivalTime = arrivalTime;
        this.seats = seats;
        this.cost = cost;
        this.train = train;
    }

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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
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
                + arrivalDate + " " + arrivalTime + " " + seats + " " + cost + " " + train;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trip trip = (Trip) o;

        return id == trip.id && seats == trip.seats
                && Objects.equals(departureDate, trip.departureDate)
                && Objects.equals(departureTime, trip.departureTime)
                && Objects.equals(arrivalDate, trip.arrivalDate)
                && Objects.equals(arrivalTime, trip.arrivalTime)
                && compareBigDecimals(cost, trip.getCost())
                && Objects.equals(train, trip.train);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, departureDate, departureTime,
                            arrivalDate, arrivalTime, duration, seats, cost, train);
    }
    private boolean compareBigDecimals(BigDecimal one, BigDecimal two){
        return one.compareTo(two) == 0;
    }
}
