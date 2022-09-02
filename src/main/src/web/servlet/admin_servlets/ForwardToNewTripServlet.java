package web.servlet.admin_servlets;
/*
  User: admin
  Cur_date: 22.08.2022
  Cur_time: 13:25
*/

import dao.DbException;
import entity.Settlement;
import entity.Train;
import org.apache.logging.log4j.LogManager;
import service.SettlementService;
import service.TrainService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/newTrip")
public class ForwardToNewTripServlet extends HttpServlet {

    private static final org.apache.logging.log4j.Logger LOG = LogManager.getLogger(ForwardToNewTripServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        TrainService trainService = (TrainService) req.getServletContext().getAttribute("trainService");
        SettlementService settlementService = (SettlementService) req.getServletContext().getAttribute("settlementService");

        try {
            List<Train> allTrains = new ArrayList<>(trainService.getTrains());
            List<Settlement> allSettlements = new ArrayList<>(settlementService.getSettlements());

            req.setAttribute("allTrains", allTrains);
            req.setAttribute("allSettlements", allSettlements);
            req.getRequestDispatcher("WEB-INF/jsp/new_trip_form.jsp").forward(req, resp);
        } catch (DbException ex){
            LOG.debug(ex.getMessage(), ex);
            resp.sendError(500, ex.getMessage());
        }

    }
}
