package web.servlet;
/*
  User: admin
  Cur_date: 27.08.2022
  Cur_time: 19:13
*/

import dao.DbException;
import entity.Trip;
import entity.User;
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
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TripService tripService = (TripService) getServletContext().getAttribute("tripService");

        try {
            int[] settings = DefaultPaginationSettings.paginationDefaultSetting(req);
            int currentPage = settings[0];
            int recordsPerPage = settings[1];

            //End pagination for user tickets
            List<Trip> userBoughtTrips =  tripService.userHasTrips(
                    new User((int) req.getSession().getAttribute("userId"))); //, currentPage, recordsPerPage
            int pagesAmount = userBoughtTrips.size() / recordsPerPage;
            if (userBoughtTrips.size() % recordsPerPage > 0)
                pagesAmount++;

            req.setAttribute("pagesAmount", pagesAmount);
            req.setAttribute("currentPage", currentPage);
            req.setAttribute("recordsPerPage", recordsPerPage);
            req.setAttribute("userTripsAmount", userBoughtTrips);

            req.getRequestDispatcher("WEB-INF/jsp/userBasket.jsp").forward(req, resp);
        } catch (DbException exception){
            exception.printStackTrace();
        }
    }
}