package web.servlet;
/*
  User: admin
  Cur_date: 09.08.2022
  Cur_time: 11:11
*/

import dao.DbException;
import entity.Trip;
import org.apache.logging.log4j.LogManager;
import service.TripService;
import util.DefaultPaginationSettings;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/menu")
public class MainMenuServlet extends HttpServlet {

    private static final org.apache.logging.log4j.Logger LOG = LogManager.getLogger(MainMenuServlet.class);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

//        resp.sendError(500, "Failed to load manu page");

        TripService tripService = (TripService) getServletContext().getAttribute("tripService");

        int[] setting = DefaultPaginationSettings.paginationDefaultSetting(req);
        int currentPage = setting[0];
        int recordsPerPage = setting[1];

        try {
            List<Trip> allTrips = tripService.getTrips(currentPage, recordsPerPage);

            int tripsAmount = tripService.getTripsAmount();
            int pagesAmount = tripsAmount / recordsPerPage;

            if (tripsAmount % recordsPerPage > 0)
                pagesAmount++;

            req.setAttribute("pagesAmount", pagesAmount);
            req.setAttribute("currentPage", currentPage);
            req.setAttribute("recordsPerPage", recordsPerPage);

            req.setAttribute("allTrips", allTrips);

            req.getRequestDispatcher("WEB-INF/jsp/menu.jsp").forward(req, resp);

        } catch (DbException ex){
            LOG.debug(ex.getMessage(), ex);
//            resp.sendError(500, ex.getMessage());
        }

    }
}
