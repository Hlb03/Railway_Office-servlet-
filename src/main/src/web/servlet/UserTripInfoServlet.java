package web.servlet;
/*
  User: admin
  Cur_date: 27.08.2022
  Cur_time: 19:13
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

@WebServlet("/userBasketInfo")
public class UserTripInfoServlet extends HttpServlet {

    private static final org.apache.logging.log4j.Logger LOG = LogManager.getLogger(UserTripInfoServlet.class);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TripService tripService = (TripService) getServletContext().getAttribute("tripService");

        try {
            int[] settings = DefaultPaginationSettings.paginationDefaultSetting(req);
            int currentPage = settings[0];
            int recordsPerPage = settings[1];

            int userId = (int) req.getSession().getAttribute("userId");
            int amount = tripService.userHasTripsAmount(userId);

            List<Trip> userBoughtTrips =  tripService.userHasTrips(userId, currentPage, recordsPerPage);
            int pagesAmount = amount / recordsPerPage;
            if (amount % recordsPerPage > 0)
                pagesAmount++;

            req.setAttribute("pagesAmount", pagesAmount);
            req.setAttribute("currentPage", currentPage);
            req.setAttribute("recordsPerPage", recordsPerPage);

            req.setAttribute("userBoughtTrips", userBoughtTrips);

            LOG.trace("User " + req.getSession().getAttribute("userLogin") + " vies his basket");
            req.getRequestDispatcher("WEB-INF/jsp/userBasket.jsp").forward(req, resp);
        } catch (DbException exception){
            req.getSession().setAttribute("errorMsg", exception.getMessage());
            resp.sendRedirect("errorHandler");
            LOG.debug(exception.getMessage(), exception);
        }
    }
}
