package web.servlet.admin_servlets;
/*
  User: admin
  Cur_date: 28.08.2022
  Cur_time: 18:09
*/

import dao.DbException;
import entity.Train;
import entity.Trip;
import service.TripService;

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

@WebServlet("/editCurrentTrip")
public class EditTripServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TripService tripService = (TripService) req.getServletContext().getAttribute("tripService");

        String depDate = req.getParameter("depDate");
        String depTime = req.getParameter("depTime");
        String arrDate = req.getParameter("arrDate");
        String arrTime = req.getParameter("arrTime");
        String train = req.getParameter("train");
        String seats = req.getParameter("seats");
        String price = req.getParameter("price");
        String tripId = req.getParameter("tripId");

        try {
            Trip t = tripService.getTrip(new Trip(Integer.parseInt(tripId)));
            Trip trip = new Trip(Date.valueOf(depDate), Time.valueOf(depTime), Date.valueOf(arrDate), Time.valueOf(arrTime), Integer.parseInt(seats),
                    BigDecimal.valueOf(Double.parseDouble(price)), new Train(train));
            trip.setId(Integer.parseInt(tripId));

            if (req.getParameter("settlement1") == null && !trip.equals(t))
                tripService.updateTripParameters(trip);
            else if (req.getParameter("settlement1") != null) {
                int stationsAmount = Integer.parseInt(req.getParameter("stationsAmount"));
                int[] tripsSettlement = new int[stationsAmount];

                //Get all settlements id from input form and put `em into array
                for (int i = 1; i <= stationsAmount; i++) {
                    String settlement = req.getParameter("settlement" + i);
                    tripsSettlement[i-1] = Integer.parseInt(settlement);
                }

                tripService.updateAllTripInfo(trip, tripsSettlement);
            }

            resp.sendRedirect("menu");
        } catch (DbException ex){
            ex.printStackTrace();
        }
    }
}
