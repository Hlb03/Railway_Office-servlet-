package web.servlet.admin_servlets;
/*
  User: admin
  Cur_date: 28.08.2022
  Cur_time: 15:39
*/

import dao.DbException;
import entity.Settlement;
import entity.Train;
import entity.Trip;
import service.SettlementService;
import service.TrainService;
import service.TripService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/editTrip")
public class ForwardToEditPageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TripService tripService = (TripService) getServletContext().getAttribute("tripService");
        SettlementService settlementService = (SettlementService)  getServletContext().getAttribute("settlementService");
        TrainService trainService = (TrainService)  getServletContext().getAttribute("trainService");

        int tripId = Integer.parseInt(req.getParameter("tripId"));
        String startStation = req.getParameter("startStation");
        String departure = req.getParameter("depDate") + " " + req.getParameter("depTime");
        String endStation = req.getParameter("endStation");
        String arrival = req.getParameter("arrDate") + " " + req.getParameter("arrTime");

        try {
            Trip t = new Trip(tripId);
            Trip trip = tripService.getTrip(t); //tripId, startStation, departure, endStation
//
            Map<Integer, Settlement> tripHasSettlement = new HashMap<>(tripService.tripContainsSettlements(trip.getId()));
            List<Settlement> allSettlements = settlementService.getSettlements();
            List<Train> allTrains = trainService.getTrains();

            req.setAttribute("allTrains", allTrains);
            req.setAttribute("allSettlements", allSettlements);
            req.setAttribute("route", trip.getStartStation() + " -- " + trip.getFinalStation());
            req.setAttribute("trip", trip);
            req.setAttribute("allStation", tripHasSettlement);

//            System.out.println(trip);
//            System.out.println(tripHasSettlement);
            req.getRequestDispatcher("WEB-INF/jsp/edit_form.jsp").forward(req, resp);
        } catch (DbException ex){
//            LOG
            ex.printStackTrace();
            resp.sendError(500);
        }
    }
}
