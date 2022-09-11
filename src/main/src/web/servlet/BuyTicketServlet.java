package web.servlet;
/*
  User: admin
  Cur_date: 25.08.2022
  Cur_time: 14:33
*/

import dao.DbException;
import entity.Trip;
import entity.User;
import org.apache.logging.log4j.LogManager;
import service.TripService;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/buyTicket")
public class BuyTicketServlet extends HttpServlet {

    private static final org.apache.logging.log4j.Logger LOG = LogManager.getLogger(BuyTicketServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserService userService = (UserService) getServletContext().getAttribute("userService");
        TripService tripService = (TripService) getServletContext().getAttribute("tripService");

        try {
            BigDecimal tripPrice = BigDecimal.valueOf(Double.parseDouble(req.getParameter("tripCost")));

            int userId = (int) req.getSession().getAttribute("userId");
            int tripId = Integer.parseInt(req.getParameter("tripId"));
            int ticketsAmount = Integer.parseInt(req.getParameter("ticketAmount"));

            User u = userService.getByLogin((String) req.getSession().getAttribute("userLogin"));

            int ticketAmount = tripService.getTripsAmount();
            List<Trip> userTrips = tripService.userHasTrips(userId, 1, ticketAmount);

            //JAVA 8 (Lambda expressions)
            List<Integer> userTripsId =  userTrips.stream().map(Trip::getId).collect(Collectors.toList());

            if (!userTripsId.contains(tripId))
                userService.userBuyTicket(userId, tripId, ticketsAmount, tripPrice);
            else userService.userBuyTripIfAlreadyPresent(userId, tripId, ticketsAmount, tripPrice);

            int userAmountOfTrips = userService.totalAmountOfUserTrips(u);

            req.getSession().setAttribute("balance", u.getBalance());
            req.getSession().setAttribute("userTrips", userAmountOfTrips);

            LOG.trace("User " + u.getLogin() + " has bought ticket(s) with id: " + tripId);
            resp.sendRedirect("menu");
        } catch (DbException ex){
            req.getSession().setAttribute("errorMsg", ex.getMessage());
            resp.sendRedirect("errorHandler");
            LOG.debug(ex.getMessage(), ex);
        }
    }
}
