package web.servlet.admin_servlets;
/*
  User: admin
  Cur_date: 19.08.2022
  Cur_time: 12:00
*/

import dao.DbException;
import entity.Train;
import org.apache.logging.log4j.LogManager;
import service.TrainService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/newTrain")
public class NewTrainServlet extends HttpServlet {

    private static final org.apache.logging.log4j.Logger LOG = LogManager.getLogger(NewTrainServlet.class);

    //What will happen if admin will add already existed train number??? -- fix
    //Add some kind of info that train was (or wasn't created)
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        TrainService tService = (TrainService) req.getServletContext().getAttribute("trainService");

        String trainNumber = req.getParameter("trainNumber");

        try {
            tService.createTrain(new Train(trainNumber));

            LOG.trace("Train with number " + trainNumber + " was created.");
            resp.sendRedirect("newTrip");
        } catch (DbException ex){
            req.getSession().setAttribute("errorMsg", ex.getMessage());
            resp.sendRedirect("errorHandler");
            LOG.debug(ex.getMessage(), ex);
        }
    }
}
