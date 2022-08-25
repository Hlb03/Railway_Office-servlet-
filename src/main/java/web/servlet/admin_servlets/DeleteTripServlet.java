package web.servlet.admin_servlets;
/*
  User: admin
  Cur_date: 24.08.2022
  Cur_time: 13:52
*/

import dao.DbException;
import entity.Trip;
import service.TripService;
import exception.FailedDeleteException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.rmi.RemoteException;

@WebServlet("/delTicket")
public class DeleteTripServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TripService tripService = (TripService) req.getServletContext().getAttribute("tripService");

        Trip trip = new Trip();
        trip.setId(Integer.parseInt(req.getParameter("tripId")));
        trip.setStartStation(req.getParameter("startStation"));
        trip.setFinalStation(req.getParameter("endStation"));

        try {
            tripService.deleteTrip(trip);
            resp.sendRedirect("menu");
        } catch (DbException ex){
            ex.printStackTrace();
        } catch (FailedDeleteException e){
            throw new RemoteException(e.getMessage());
        }
    }
}
