package web.servlet.admin_servlets;
/*
  User: admin
  Cur_date: 26.08.2022
  Cur_time: 13:58
*/

import dao.DbException;
import org.apache.logging.log4j.LogManager;
import service.TripService;
import web.servlet.UserTripInfoServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/deleteAllOutdated")
public class DeleteAllOutdatedTripsServlet extends HttpServlet {

    private static final org.apache.logging.log4j.Logger LOG = LogManager.getLogger(DeleteAllOutdatedTripsServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TripService tripService = (TripService) getServletContext().getAttribute("tripService");

        try {
            tripService.deleteAllOutdatedTrips();

            LOG.trace("All outdated trips were deleted.");
            resp.sendRedirect("menu");
        } catch (DbException ex){
            req.getSession().setAttribute("errorMsg", ex.getMessage());
            resp.sendRedirect("errorHandler");
            LOG.debug(ex.getMessage(), ex);
        }
    }
}
