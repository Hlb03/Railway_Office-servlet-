package web.servlet;
/*
  User: admin
  Cur_date: 09.08.2022
  Cur_time: 11:11
*/

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import dao.DbException;
import entity.Trip;
import service.TripService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/menu")
public class MainMenuServlet extends HttpServlet {

    private static final Logger LOG = LoggerFactory.getLogger(MainMenuServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        LOG.debug("MainMenu#doGet");
        LOG.debug("Forwarding to menu.jsp");

        TripService tripService = (TripService) getServletContext().getAttribute("tripService");

        String currPage = req.getParameter("currentPage");
        String recPerPage = req.getParameter("recordsPerPage");
        int currentPage;
        int recordsPerPage;

        if (currPage == null){
            currentPage = 1;
            recordsPerPage = 5; //changes an amount of data on page
        } else {
            currentPage = Integer.parseInt(currPage);
            recordsPerPage = Integer.parseInt(recPerPage);
        }

//        List<Trip> allTrips;
//        int pagesAmount;

        try {
            List<Trip> allTrips = tripService.getTrips(currentPage, recordsPerPage);
            int pagesAmount = tripService.getTripsAmount() / recordsPerPage;

            if (tripService.getTripsAmount() % recordsPerPage > 0)
                pagesAmount++;


            req.setAttribute("pagesAmount", pagesAmount);
            req.setAttribute("currentPage", currentPage);
            req.setAttribute("recordsPerPage", recordsPerPage);

            req.setAttribute("allTrips", allTrips);

            req.getRequestDispatcher("WEB-INF/jsp/menu.jsp").forward(req, resp);
        } catch (DbException ex){
            //LOG
            System.out.println("Failed to get all trains/trips");
        }

    }
}
