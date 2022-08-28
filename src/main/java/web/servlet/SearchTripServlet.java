package web.servlet;
/*
  User: admin
  Cur_date: 16.08.2022
  Cur_time: 14:01
*/

import dao.DbException;
import entity.Trip;
import service.TripService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/searchTrip")
public class SearchTripServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String startStation = req.getParameter("from");
        String finalStation = req.getParameter("to");
        String date = req.getParameter("date");

//        if (date == null)
//            System.out.println("Date is not present");
        if (date.isEmpty())
            System.out.println("Date is empty");

        PrintWriter pw = resp.getWriter();

        TripService tService = (TripService) getServletContext().getAttribute("tripService");

        List<Trip> trips = null;
        try {
            trips = tService.getByRoute(startStation, finalStation);
            if (trips.isEmpty())
                resp.getWriter().println("There is no such a train for this date");

        } catch (DbException ex){
            //LOG
            System.out.println("Can't take in search servlet");
        }

        pw.println("<html>");
        pw.println("<body>");
        for (Trip t : trips) {
            pw.println("<p>" + t + "</p>");
        }
        pw.println("</p>");
        pw.println("</body>");
        pw.println("</html>");
    }
}
