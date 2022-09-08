package web.servlet.admin_servlets;
/*
  User: admin
  Cur_date: 28.08.2022
  Cur_time: 15:39
*/

import com.sun.org.apache.xpath.internal.operations.String;
import dao.DbException;
import entity.Settlement;
import entity.Train;
import entity.Trip;
import org.apache.logging.log4j.LogManager;
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

    private static final org.apache.logging.log4j.Logger LOG = LogManager.getLogger(ForwardToEditPageServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TripService tripService = (TripService) getServletContext().getAttribute("tripService");
        SettlementService settlementService = (SettlementService)  getServletContext().getAttribute("settlementService");
        TrainService trainService = (TrainService)  getServletContext().getAttribute("trainService");

        int tripId = Integer.parseInt(req.getParameter("tripId"));

        StringBuilder url = new StringBuilder();
        url.append("editTrip?tripId=").append(tripId);

        req.setAttribute("url", url.toString());
        try {
            Trip t = new Trip(tripId);
            Trip trip = tripService.getTrip(t);

            Map<Integer, Settlement> tripHasSettlement = new HashMap<>(tripService.tripContainsSettlements(trip.getId()));
            List<Settlement> allSettlements = settlementService.getSettlements();
            List<Train> allTrains = trainService.getTrains();

            req.setAttribute("allTrains", allTrains);
            req.setAttribute("allSettlements", allSettlements);
            req.setAttribute("route", trip.getStartStation() + " -- " + trip.getFinalStation());
            req.setAttribute("trip", trip);
            req.setAttribute("allStation", tripHasSettlement);

            req.getRequestDispatcher("WEB-INF/jsp/edit_form.jsp").forward(req, resp);
        } catch (DbException ex){
            req.getSession().setAttribute("errorMsg", ex.getMessage());
            resp.sendRedirect("errorHandler");
            LOG.debug(ex.getMessage(), ex);
        }
    }
}
