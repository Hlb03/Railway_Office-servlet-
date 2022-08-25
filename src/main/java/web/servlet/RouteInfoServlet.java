package web.servlet;
/*
  User: admin
  Cur_date: 20.08.2022
  Cur_time: 13:49
*/

import dao.DbException;
import entity.Settlement;
import entity.Trip;
import service.TripService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/routeInfo")
public class RouteInfoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TripService tService = (TripService) req.getServletContext().getAttribute("tripService");

        int id = Integer.parseInt(req.getParameter("trip_id"));
        String start = req.getParameter("start");
        String departure = req.getParameter("depart");
        String end = req.getParameter("destination");

        try {
            Trip trip = tService.getTrip(id, start, departure, end);
            Map<Integer, Settlement> tripHasSettlement = new HashMap<>(tService.tripContainsSettlements(trip.getId()));

            req.setAttribute("route", trip.getStartStation() + " -- " + trip.getFinalStation());
            req.setAttribute("trip", trip);
            req.setAttribute("allStation", tripHasSettlement);

            req.getRequestDispatcher("WEB-INF/jsp/ticket_view.jsp").forward(req, resp);
        } catch (DbException ex){
            //LOG
            ex.printStackTrace();
//            resp.sendError(500);
        }


    }
}
