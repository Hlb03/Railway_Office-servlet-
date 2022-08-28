package web.servlet.admin_servlets;
/*
  User: admin
  Cur_date: 22.08.2022
  Cur_time: 16:19
*/

import dao.DbException;
import entity.Train;
import entity.Trip;
import service.TripService;
import util.TimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.Arrays;

@WebServlet("/addTrip")
public class AddNewTripServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TripService tripService = (TripService) req.getServletContext().getAttribute("tripService");

        String depDate = req.getParameter("depDate");
        String depTime = TimeFormatter.addZeroMilliseconds(req.getParameter("depTime"));
        String arrDate = req.getParameter("arrDate");
        String arrTime = TimeFormatter.addZeroMilliseconds(req.getParameter("arrTime"));
        String train = req.getParameter("train");
        String seats = req.getParameter("seats");
        String price = req.getParameter("price");

        int stationsAmount = Integer.parseInt(req.getParameter("stationsAmount"));
        Integer[] tripsSettlement = new Integer[stationsAmount];

        //Get all settlements id from input form and put `em into array
        for (int i = 1; i <= stationsAmount; i++) {
            String settlement = req.getParameter("settlement" + i);
            tripsSettlement[i-1] = Integer.parseInt(settlement);
        }

        Trip trip = new Trip();
        trip.setDepartureDate(Date.valueOf(depDate));
        trip.setDepartureTime(Time.valueOf(depTime));
        trip.setArrivalDate(Date.valueOf(arrDate));
        trip.setArrivalTime(Time.valueOf(arrTime));
        trip.setSeats(Integer.parseInt(seats));
        trip.setCost(BigDecimal.valueOf(Double.parseDouble(price)));
        trip.setTrain(new Train(Integer.parseInt(train)));

        System.out.println(trip);
        System.out.println(Arrays.toString(tripsSettlement));

        try {
            tripService.createTrip(trip, tripsSettlement[0], tripsSettlement[tripsSettlement.length - 1], tripsSettlement);
            resp.sendRedirect("menu");
        } catch (DbException ex) {
            ex.printStackTrace();
        }

    }
}
