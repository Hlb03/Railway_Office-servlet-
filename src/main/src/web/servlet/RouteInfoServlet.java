package web.servlet;
/*
  User: admin
  Cur_date: 20.08.2022
  Cur_time: 13:49
*/

import dao.DbException;
import entity.Settlement;
import entity.Trip;
import org.apache.logging.log4j.LogManager;
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

    private static final org.apache.logging.log4j.Logger LOG = LogManager.getLogger(RouteInfoServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TripService tService = (TripService) req.getServletContext().getAttribute("tripService");

        String url = "routeInfo?trip_id=" +
                req.getParameter("trip_id") +
                "&start=" +
                req.getParameter("start") +
                "&depart=" +
                req.getParameter("depart");

        req.setAttribute("url", url);

        int id = Integer.parseInt(req.getParameter("trip_id"));
        String buyOpportunity = req.getParameter("buy");

        try {
            Trip trip = tService.getTrip(id);
            Map<Integer, Settlement> tripHasSettlement = new HashMap<>(tService.tripContainsSettlements(id));

            req.setAttribute("route", trip.getStartStation() + " -- " + trip.getFinalStation());
            req.setAttribute("trip", trip);
            req.setAttribute("allStation", tripHasSettlement);
            req.setAttribute("buyOpportunity", buyOpportunity);

            LOG.trace("Detailed info about: " + trip);
            req.getRequestDispatcher("WEB-INF/jsp/ticket_view.jsp").forward(req, resp);
        } catch (DbException ex){

            req.getSession().setAttribute("errorMsg", ex.getMessage());
            resp.sendRedirect("errorHandler");
            LOG.debug(ex.getMessage(), ex);
        }


    }
}
