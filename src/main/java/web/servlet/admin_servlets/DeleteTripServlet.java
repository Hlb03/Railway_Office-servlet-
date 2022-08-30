package web.servlet.admin_servlets;
/*
  User: admin
  Cur_date: 24.08.2022
  Cur_time: 13:52
*/

import dao.DbException;
import entity.Trip;
import org.apache.logging.log4j.LogManager;
import service.TripService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/delTicket")
public class DeleteTripServlet extends HttpServlet {

    private static final org.apache.logging.log4j.Logger LOG = LogManager.getLogger(DeleteTripServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TripService tripService = (TripService) req.getServletContext().getAttribute("tripService");

        Trip trip = new Trip();
        trip.setId(Integer.parseInt(req.getParameter("tripId")));
        trip.setStartStation(req.getParameter("startStation"));
        trip.setFinalStation(req.getParameter("endStation"));

        try {
            tripService.deleteTrip(trip);

            LOG.trace("Trip " + trip.getId() + " " + trip.getStartStation() + " " + trip.getFinalStation() + " was deleted.");
            resp.sendRedirect("menu");
        } catch (DbException ex){
            LOG.debug(ex.getMessage(), ex);
        }
    }
}
