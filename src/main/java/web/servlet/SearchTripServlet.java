package web.servlet;
/*
  User: admin
  Cur_date: 16.08.2022
  Cur_time: 14:01
*/

import dao.DbException;
import entity.Trip;
import service.TripService;
import util.DefaultPaginationSettings;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

@WebServlet("/searchTrip")
public class SearchTripServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TripService tService = (TripService) getServletContext().getAttribute("tripService");

        String startStation = req.getParameter("from");
        String finalStation = req.getParameter("to");
        String date = req.getParameter("date");

        date = date.trim();

        int[] setting = DefaultPaginationSettings.paginationDefaultSetting(req);
        int currentPage = setting[0];
        int recordsPerPage = setting[1];

        List<Trip> tripsFromSearch;
        try {
            int amountOfData = 0;


            if (date.isEmpty()) {
                tripsFromSearch = tService.getByRoute(startStation, finalStation, currentPage, recordsPerPage);
                amountOfData = tService.amountOfFountTripsByRoute(startStation, finalStation);
            } else
                tripsFromSearch = tService.getByRouteAndDate(startStation, finalStation, Date.valueOf(date));

            if (tripsFromSearch.isEmpty())
                System.out.println("There is no such route");

            int pagesAmount = amountOfData / recordsPerPage;
            if (amountOfData % recordsPerPage > 0)
                pagesAmount++;


            req.setAttribute("tripsFromSearch", tripsFromSearch);


            req.setAttribute("from", startStation);
            req.setAttribute("to", finalStation);
            req.setAttribute("date", date);

            req.setAttribute("pagesAmount", pagesAmount);
            req.setAttribute("currentPage", currentPage);
            req.setAttribute("recordsPerPage", recordsPerPage);

            req.getRequestDispatcher("WEB-INF/jsp/menu.jsp").forward(req, resp);
        } catch (DbException ex){
            //LOG
            System.out.println("Can't take in search servlet");
        }
    }
}
